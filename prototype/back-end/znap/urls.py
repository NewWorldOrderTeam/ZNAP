"""znap URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.10/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url, include
from django.contrib import admin
from rest_framework import routers

from adminapi.views import AdminLoginAPIView
from queueapi.views import QueueViewSet, QueueCreateAPIView
from rateapi.views import RateViewSet, RateCreateAPIView, AddMessageAPIView
from userapi.views import UserCreateAPIView, UserLoginAPIView, UserViewSet

router = routers.DefaultRouter()
router.register(r'user',UserViewSet)
router.register(r'rate', RateViewSet)
router.register(r'queue', QueueViewSet)


urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^api/v1.0/', include(router.urls)),
    url(r'^api-auth/', include('rest_framework.urls', namespace='rest_framework') ),
    url(r'^api/v1.0/register/', UserCreateAPIView.as_view(), name='register'),
    url(r'^api/v1.0/login/', UserLoginAPIView.as_view(), name='login'),
    url(r'^api/v1.0/adminlogin/', AdminLoginAPIView.as_view(), name='adminlogin'),
    url(r'^api/v1.0/addrate/', RateCreateAPIView.as_view(), name='create rate'),
    url(r'^api/v1.0/addmessage/', AddMessageAPIView.as_view(), name='add message'),
    url(r'^api/v1.0/registerToQueue/',QueueCreateAPIView.as_view(), name='register to queue')
]
