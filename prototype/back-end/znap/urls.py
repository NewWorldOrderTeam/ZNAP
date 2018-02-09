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
from rest_framework.routers import DefaultRouter

import userapi
from adminapi.views import AdminLoginAPIView, AdminViewSet
from queueapi.views import QueueViewSet, QueueCreateAPIView
from rateapi.views import RateViewSet, RateCreateAPIView, AddMessageAPIView, DialogViewSet
from userapi.views import UserCreateAPIView, UserLoginAPIView, UserViewSet

from rest_framework_extensions.routers import NestedRouterMixin

from znapapi.views import ZnapViewSet, RegistrationToZnapCreateAPIView, RegistrationToZnapViewSet, QlogicCnapViewSet, \
    QlogicServicesViewSet, QlogicDaysForServiceViewSet


class NestedDefaultRouter(NestedRouterMixin, DefaultRouter):
    pass

router = NestedDefaultRouter()
router.register(r'queue', RegistrationToZnapViewSet)

dialog_router = router.register('rate', RateViewSet)
dialog_router.register('dialog', DialogViewSet,
                       base_name='rate-dialog',
                       parents_query_lookups=['dialog'])
user_router = router.register('user', UserViewSet)
user_router.register('rate', RateViewSet,
                     base_name='user-rate',
                     parents_query_lookups=['user'])
admin_router= router.register('admin', AdminViewSet)
admin_router.register('rate', RateViewSet,
                      base_name='admin-rate',
                      parents_query_lookups=['admin'])
znap_router = router.register('znap', ZnapViewSet)
znap_router.register('rate', RateViewSet,
                     base_name='znap-rate',
                     parents_query_lookups=['znap'])


urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^api/v1.0/', include(router.urls)),
    url(r'^api-auth/', include('rest_framework.urls', namespace='rest_framework') ),
    url(r'^api/v1.0/register/', UserCreateAPIView.as_view(), name='register'),
    url(r'^api/v1.0/login/', UserLoginAPIView.as_view(), name='login'),
    url(r'^api/v1.0/adminlogin/', AdminLoginAPIView.as_view(), name='adminlogin'),
    url(r'^api/v1.0/addrate/', RateCreateAPIView.as_view(), name='create rate'),
    url(r'^api/v1.0/addmessage/', AddMessageAPIView.as_view(), name='add message'),
    url(r'^api/v1.0/registerToQueue/',RegistrationToZnapCreateAPIView.as_view(), name='register to queue'),
    url(r'^activate/(?P<uidb64>[0-9A-Za-z_\-]+)/(?P<token>[0-9A-Za-z]{1,13}-[0-9A-Za-z]{1,20})/', userapi.views.activate, name='activate'),
    url(r'^api/v1.0/cnap/(?P<service_center>[0-9])/services/', QlogicServicesViewSet.as_view(), name='cnap services'),
    url(r'^api/v1.0/cnap/', QlogicCnapViewSet.as_view(), name='cnap'),
    url(r'^api/v1.0/getDays/cnap/(?P<service_center>[0-9])/service/(?P<service>[0-9])/', QlogicDaysForServiceViewSet.as_view(), name='days for services')
]
