# coding=utf-8
import json
import urllib

from rest_framework import viewsets

from rest_framework.permissions import AllowAny
from rest_framework.generics import CreateAPIView

from rest_framework.response import Response
from rest_framework.views import APIView


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
            name = name.replace(u'Центр надання адміністративних послуг ', '').replace(u'ЦНАП ', '').replace(u'м.Львова ', '').replace(u'на ', '')
            count = service_center['CountOfWaitingJobs']
            color = service_center['Color']
            json_queue.append({'name':name,
                              'count':count,
                              'color':color})

        return Response(json_queue)
