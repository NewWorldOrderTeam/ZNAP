import json, urllib

import requests
from django.shortcuts import render

# Create your views here.
from rest_framework import viewsets
from rest_framework.generics import CreateAPIView
from rest_framework.permissions import AllowAny
from rest_framework.response import Response
from rest_framework.views import APIView

from znapapi.models import Znap, RegistrationToZnap
from znapapi.serializers import ZnapSerialezer, CreateRegistrationToZnapSerializer, RegistrationToZnapSerializer


class ZnapViewSet(viewsets.ModelViewSet):
    permission_classes = [AllowAny]
    queryset = Znap.objects.all()
    serializer_class = ZnapSerialezer

class QlogicCnapViewSet(APIView):
    permission_classes = [AllowAny]

    def get(self, request):
        organisationGuid = '{28c94bad-f024-4289-a986-f9d79c9d8102}'

        url = 'http://qlogic.net.ua:8084/QueueService.svc/json_pre_reg/getServiceCenterList?organisationGuid='+organisationGuid
        r = urllib.urlopen(url).read()
        cnaps = json.loads(r)
        cnaps_list = cnaps['d']

        json_cnap = []
        for cnap in cnaps_list:
            name = cnap['ServiceCenterName']
            service_id = cnap['ServiceCenterId']
            json_cnap.append({'name':name,
                              'service_center_id': service_id})
        return Response(json_cnap)

class RegistrationToZnapCreateAPIView(CreateAPIView):
    permission_classes = [AllowAny]
    serializer_class = CreateRegistrationToZnapSerializer
    queryset = RegistrationToZnap.objects.all()

class RegistrationToZnapViewSet(viewsets.ModelViewSet):
    permission_classes = [AllowAny]
    queryset = RegistrationToZnap.objects.all()
    serializer_class = RegistrationToZnapSerializer

class QlogicServicesViewSet(APIView):
    permission_classes = [AllowAny]

    def get(self, request, service_center):
        organisationGuid = '{28c94bad-f024-4289-a986-f9d79c9d8102}'

        url = 'http://qlogic.net.ua:8084/QueueService.svc/json_pre_reg/GetServiceList?organisationGuid=' + organisationGuid + '&serviceCenterId=' + service_center
        r = urllib.urlopen(url).read()
        services = json.loads(r)

        return Response(services['d'])

class QlogicDaysForServiceViewSet(APIView):
    permission_classes = [AllowAny]

    def get(self, request, service_center, service):
        organisationGuid = '{28c94bad-f024-4289-a986-f9d79c9d8102}'

        url = 'http://qlogic.net.ua:8084/QueueService.svc/json_pre_reg/GetDayList?organisationGuid=' + organisationGuid + '&serviceCenterId=' + service_center + '&serviceId=' + service

        r = urllib.urlopen(url).read()
        days = json.loads(r)

        return Response(days['d'])