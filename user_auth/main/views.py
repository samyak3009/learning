from django.shortcuts import render
from random import randint
import string 
import random 
import boto3,base64
from datetime import datetime

from django.shortcuts import render
from django.db.models import Q,Count
from django.contrib.auth import authenticate, login
from rest_framework_simplejwt.serializers import TokenObtainPairSerializer
from rest_framework_simplejwt.views import TokenObtainPairView
from rest_framework.response import Response
from rest_framework.views import APIView
from rest_framework import generics, status, permissions, filters
from django.core.mail import send_mail
from .models import *
from .serializers import *
def responsedata(status, message, data=None):
    if status:
        return {"status":status,"message":message,"data":data}
    else:
        return {"status":status,"message":message}

def django_send_mail(recipient, subject, body, msg_html=None):
    send_mail(
        subject,
        body,
        'nancy.a1999@gmail.com',
        [recipient],
        fail_silently=True,
    )  

class RegisterUser(APIView):

    token_obtain_pair = TokenObtainPairView.as_view()

    def post(self, request):
        #validation to check whether email already exists
        if User.objects.filter(email=request.data.get('email')).exists():
            return Response(responsedata(False, "User Email already present"), status=status.HTTP_400_BAD_REQUEST)
        #validation if confirm password and password does not match
        if request.data.get("confirm_password") != request.data.get("password"):
            return Response(responsedata(False, "Password Does Not Match!!"), status=status.HTTP_400_BAD_REQUEST)
        if request.data:
            data = request.data
            serializer = UserSerializer(data=data)        
            if serializer.is_valid(raise_exception=True):
                serializer.save()  
            user_data = serializer.data
            django_send_mail(request.data.get('email'),"Registration Successfull","CONGRATULATIONS!!!!")
            return Response(responsedata(True, "Data Inserted",user_data), status=status.HTTP_200_OK)
        return Response(responsedata(False, "No Data provided"), status=status.HTTP_400_BAD_REQUEST)

    
            
class LoginUser(TokenObtainPairView):
    """To login user using email/mobile and password"""

    token_obtain_pair = TokenObtainPairView.as_view()

    def post(self, request, *args, **kwargs):
        #validation for password is required
        if not request.data.get("password"):
            return Response(responsedata(False, "Password is required"), status=status.HTTP_400_BAD_REQUEST)
        #validation if user with given email id does not exists
        if not User.objects.filter(email=request.data.get('email')).exists():
            return Response(responsedata(False, "No user found"), status=status.HTTP_400_BAD_REQUEST)
        #validation to check password
        if not User.objects.get(email=request.data.get('email')).check_password(request.data.get("password")):
            return Response(responsedata(False, "Incorrect Password"), status=status.HTTP_400_BAD_REQUEST)
        #to login user
        if request.data.get('email'):
            user = User.objects.get(email=request.data.get('email'))
            request.data['uuid'] = user.uuid
            user = authenticate(email=request.data.get('email'), password=request.data.get('password'))
            login(request,user)
        serializer = TokenObtainPairSerializer(data=request.data)

        if serializer.is_valid(raise_exception=True):
            data = serializer.validate(request.data)
            #to get user data
            data['user'] = User.objects.filter(uuid=request.data.get('uuid')).values()
            return Response(responsedata(True, "Sign in Successful", data), status=status.HTTP_200_OK)
        return Response(responsedata(False, "Something went wrong"), status=status.HTTP_400_BAD_REQUEST)

class UserProfile(APIView):
    #DB table to be used
    model_class = User
    #serializer to use
    serializer_class = UserSerializer
    #permissions
    permission_classes = [permissions.AllowAny]
    instance_name = 'User'

    #to get the user object according to PK
    def get_object(self, pk):

        try:
            return self.model_class.objects.get(pk=pk)
        except self.model_class.DoesNotExist:
            raise ValidationError({
                'status': False,
                'message': f"failed to find {self.instance_name}",
                "data": {}
            })

    def get(self, request, pk=None, format=None):
        obj = self.get_object(pk)
        serializer = self.serializer_class(obj)
        return Response(
            data={
                "status": True,
                "message":f"{self.instance_name} reterived sucessfully",
                "data": serializer.data
            })

    
class AddFavourites(APIView):
    #DB table to be used
    model_class = User
    #serializer to use
    serializer_class = UserSerializer
    #permissions
    permission_classes = [permissions.AllowAny]
    instance_name = 'User Favourites'

    def get_object(self, pk):
        try:
            return self.model_class.objects.get(pk=pk)
        except self.model_class.DoesNotExist:
            raise ValidationError({
                'status': False,
                'message': f"failed to find {self.instance_name}",
                "data": {}
            })

    def put(self, request, pk=None, format=None):
        import pdb;pdb.set_trace()
        obj = self.get_object(pk)
        if obj.favourites== None:
            data = request.data
            serializer = self.serializer_class(obj, data=request.data, partial=True)
            if serializer.is_valid():
                serializer.save()
        else:
            obj.favourites.append(request.data['favourites'])
            obj.save()
        return Response(
                data={
                    "status": True,
                    "message": f"{self.instance_name} updated sucessfully"
                })
        return Response(data={
            "status": False,
            "message": f"{self.instance_name} update failed"
            },
                        status=status.HTTP_400_BAD_REQUEST)

    
class RemoveFavourites(APIView):
    #DB table to be used
    model_class = User
    #serializer to use
    serializer_class = UserSerializer
    #permissions
    permission_classes = [permissions.AllowAny]
    instance_name = 'User Data'

    def get_object(self, pk):
        try:
            return self.model_class.objects.get(pk=pk)
        except self.model_class.DoesNotExist:
            raise ValidationError({
                'status': False,
                'message': f"failed to find {self.instance_name}",
                "data": {}
            })

    def put(self, request, pk=None, format=None):
        obj = self.get_object(pk)
        if obj.favourites== None:
            return Response(data={
            "status": False,
            "message": f"{self.instance_name} update failed because no favourites added"
            },
                        status=status.HTTP_400_BAD_REQUEST)
        else:
            obj.favourites.pop()
            obj.save()
        return Response(
                data={
                    "status": True,
                    "message": f"{self.instance_name} updated sucessfully"
                })
        return Response(data={
            "status": False,
            "message": f"{self.instance_name} update failed"
            },
                        status=status.HTTP_400_BAD_REQUEST)
