# coding=utf-8
from datetime import datetime, date

from rest_framework import serializers
from rest_framework.fields import DateField

from queueapi.models import servicesForCNAP, cnapWithService, infoAboutCnap


class QueueSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = infoAboutCnap
        fields = ('znapName',)

class ChoicesField(serializers.Field):
     def __init__(self, choices, **kwargs):
         self._choices = choices
         super(ChoicesField, self).__init__(**kwargs)

     def to_representation(self, obj):
         return self._choices[obj]

     def to_internal_value(self, data):
        return getattr(self._choices, data)


class QueueCreateSerializer(serializers.ModelSerializer):
    dateOfRegistration = DateField(default=date.today())
    serviceType = ChoicesField(choices=servicesForCNAP.typeForServices, default=servicesForCNAP.typeForServices.Post)
    #serviceName = ChoicesField(choices=servicesForCNAP.namesForServices,default=servicesForCNAP.namesForServices.ОтриматиПаспорт)
    class Meta:
        model = cnapWithService

        fields = ['nameOfZnap','dateOfRegistration','serviceType','status','serviceName']

    def create(self, validated_data):
        nameOfZnap = validated_data['nameOfZnap']
        dateOfRegistration = validated_data['dateOfRegistration']
        queueObj = cnapWithService(
            nameOfZnap=nameOfZnap,
            dateOfRegistration=dateOfRegistration,
            status=False
        )
        queueObj.save()
        return validated_data