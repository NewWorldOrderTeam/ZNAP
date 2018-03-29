import base64
import json, urllib
import datetime
import string

import requests
from django.shortcuts import render

# Create your views here.
from rest_framework import viewsets, status
from rest_framework.generics import CreateAPIView
from rest_framework.permissions import AllowAny
from rest_framework.response import Response
from rest_framework.status import HTTP_400_BAD_REQUEST
from rest_framework.views import APIView

from znap.AES import encryption, decryption
from znap.settings import organisationGuid
from znapapi.models import Znap, RegistrationToZnap
from znapapi.serializers import ZnapSerialezer, CreateRegistrationToZnapSerializer, RegistrationToZnapSerializer, \
    QlogicFinishRegistrationToZnapSerializer


class ZnapViewSet(viewsets.ModelViewSet):
    permission_classes = [AllowAny]
    queryset = Znap.objects.all()
    serializer_class = ZnapSerialezer

class RegistrationToZnapCreateAPIView(CreateAPIView):
    permission_classes = [AllowAny]
    serializer_class = CreateRegistrationToZnapSerializer
    queryset = RegistrationToZnap.objects.all()

class RegistrationToZnapViewSet(viewsets.ModelViewSet):
    permission_classes = [AllowAny]
    queryset = RegistrationToZnap.objects.all()
    serializer_class = RegistrationToZnapSerializer

class QlogicCnapViewSet(APIView):
    permission_classes = [AllowAny]

    def get(self, request):

        url = 'http://qlogic.net.ua:8084/QueueService.svc/json_pre_reg/getServiceCenterList?organisationGuid='+organisationGuid
        r = urllib.urlopen(url).read()
        cnaps = json.loads(r)
        cnaps_list = cnaps['d']

        json_cnap = []
        for cnap in cnaps_list:
            name =cnap['LocationName']
            service_id = cnap['ServiceCenterId']
            json_cnap.append({'name':name,
                              'id': service_id})
        return Response(json_cnap)

class QlogicGroupViewSet(APIView):
    permission_classes = [AllowAny]

    def get(self, request, service_center):
        url = 'http://qlogic.net.ua:8084/QueueService.svc/json_wellcome_point/getGroupsByCenterId?organisationGuid='+organisationGuid+'&serviceCenterId='+service_center+'&preliminary=1'
        r = urllib.urlopen(url).read()
        groups = json.loads(r)
        group_list = groups['d']
        json_group = []
        for group in group_list:
            if group['IsActive']==1:
                description = group['Description']
                group_id = group['GroupId']
                json_group.append({'description':description,
                                   'id':group_id})
        return Response(json_group)


class QlogicServicesViewSet(APIView):
    permission_classes = [AllowAny]

    def get(self, request, service_center, group):

        url = 'http://qlogic.net.ua:8084/QueueService.svc/json_wellcome_point/getServicesByCenterId?organisationGuid='+organisationGuid+'&serviceCenterId='+service_center+'&groupId='+group+'&preliminary=1'
        r = urllib.urlopen(url).read()
        services = json.loads(r)
        services_list = services['d']
        json_services = []
        for service in services_list:
            if service['IsActive']==1:
                description = service['Description']
                id = service['ServiceId']
                json_services.append({'description':description,
                                      'id':id})

        return Response(json_services)

class QlogicTimeForServiceViewSet(APIView):
    permission_classes = [AllowAny]

    def get(self, request, service_center, service):

        url = 'http://qlogic.net.ua:8081/WebPreRegistration/GetServiceCenterInfo?orgKey=' + organisationGuid + '&srvCenterId='+service_center

        r = urllib.urlopen(url).read()
        json_data = r.split('(',1)[1].rsplit(')', 1)[0]
        days = json.loads(json_data)
        current_day = days['currentDay'].split('(')[1].split(')')[0][0:10]
        last_day = days['lastDateTime'].split('(')[1].split(')')[0][0:10]

        current_day = datetime.datetime.fromtimestamp(int(current_day)).strftime('%m-%d-%Y')
        last_day = datetime.datetime.fromtimestamp(int(last_day)).strftime('%m-%d-%Y')

        url = 'http://qlogic.net.ua:8084/QueueService.svc/json_pre_reg/GetDayIntervalListEx?organisationGuid='+organisationGuid+'&serviceCenterId='+service_center+'&serviceId='+service+'&dateStart='+current_day+'&dateEnd='+last_day
        r = urllib.urlopen(url).read()
        time_list = json.loads(r)['d']
        json_time = []

        for time in time_list:
            if time['IsAllow']==1:
                day = time['DatePart'].split('(')[1].split(')')[0][0:10]
                day = datetime.datetime.fromtimestamp(int(day)).strftime('%Y-%m-%d')
                hours = time['Times']
                json_hour = []
                for hour in hours:
                    if hour['IsAllow']==1:
                        start = hour['StartTime']
                        start = start[2:]
                        start = start.replace('H', ':')
                        if len(start) ==2:
                            start = '0'+start+'00'
                        elif len(start)==3:
                            start = start + '00'
                        elif 'M' in start:
                            start = start.replace('M', '')
                        stop = hour['StopTime']
                        stop = stop[2:]
                        stop = stop.replace('H', ':')
                        if len(stop) ==2:
                            stop = '0'+stop+'00'
                        elif len(stop)==3:
                            stop = stop + '00'
                        elif 'M' in stop:
                            stop = stop.replace('M', '')
                        json_hour.append({'start':start, 'stop':stop})
                json_time.append({'day': day, 'times':json_hour})

        return Response(json_time)

class QlogicFinishRegistration(CreateAPIView):
    permission_classes = [AllowAny]
    serializer_class = QlogicFinishRegistrationToZnapSerializer
    queryset = ''



class QlogicQueueStateViewSet(APIView):
    permission_classes = [AllowAny]

    def get(self, request):
        url = 'http://qlogic.net.ua:8081/Chart/ChartByNow?orgKey=28c94bad-f024-4289-a986-f9d79c9d8102'

        r = urllib.urlopen(url).read()

        queue = json.loads(r)
        queue_list = queue['data']
        json_queue = []

        for service_center in queue_list:
            name = service_center['SrvCenterDescription']
            count = service_center['CountOfWaitingJobs']
            json_queue.append({'name':name,
                              'count':count})

        return Response(json_queue)





