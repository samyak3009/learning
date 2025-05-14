from rest_framework import serializers
from django.contrib.auth.hashers import make_password
import re
from .models import *

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = '__all__'

    def validate_password(self, str) -> str:
        """ A function to save the password for storing the values """
        return make_password(str)
