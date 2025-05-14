from django.db import models
import uuid  # for generating uuid
import datetime

from django.contrib.postgres.fields import ArrayField
from django.contrib.auth.base_user import AbstractBaseUser
from django.contrib.auth.models import PermissionsMixin
from .managers import UserManager
from django_mysql.models import ListTextField

# base model
class BaseModel(models.Model):
    """Base ORM model"""
    # create uuid field
    uuid = models.UUIDField(primary_key=True, default=uuid.uuid4, editable=False)

    # created and updated at date
    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)

    # meta class
    class Meta:
        abstract = True

    # Time elapsed since creation
    def get_seconds_since_creation(self):
        """
        Find how much time has been elapsed since creation, in seconds.
        This function is timezone agnostic, meaning this will work even if
        you have specified a timezone.
        """
        return (datetime.datetime.utcnow() -
                self.created_at.replace(tzinfo=None)).seconds


# User model table
class User(BaseModel, AbstractBaseUser, PermissionsMixin):
    """A ORM model for Managing User and Authentication"""

    # mobile field
    mobile = models.BigIntegerField(unique=True,null =True)
    email =  models.EmailField(max_length = 254,unique=True,null = True,blank=True) 
    full_name = models.CharField(max_length=100,null=True,blank=True)
    favourites = ListTextField(
        base_field=models.CharField(max_length=200),
        size=100,default=None,null=True
    )
    city = models.CharField(max_length=100,null=True,blank=True)
    dob = models.DateField(null=True,blank=True)
    password = models.CharField(max_length=100)
    gender =models.CharField(max_length=100,null=True,blank=True)
    is_staff = models.BooleanField(default=False)
    is_active = models.BooleanField(default=True)
    is_superuser = models.BooleanField(default=False)
    
    # create objs for management
    objects = UserManager()

    # SET email field as username
    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = []

    # create a meta class
    class Meta:
        db_table= 'user'
