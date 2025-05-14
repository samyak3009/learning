from django.contrib import admin
from django.urls import path
from main import views
urlpatterns = [
    path('registeruser', views.RegisterUser.as_view()),
    path('loginuser', views.LoginUser.as_view()),
    path('userprofile/<pk>', views.UserProfile.as_view()),
    path('addfavourites/<pk>', views.AddFavourites.as_view()),
    path('removefavourites/<pk>', views.RemoveFavourites.as_view()),
]