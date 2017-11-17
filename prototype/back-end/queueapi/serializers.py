import datetime
from django.forms import CharField, DateField, DateTimeField, BooleanField
from rest_framework import serializers
from queueapi.models import infoAboutCnap, servicesForCNAP, cnapWithService
from znap import settings


class QueueSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = servicesForCNAP
        fields = ('znap','serviceType','date','service')

class ChoicesField(serializers.Field):
     def __init__(self, choices, **kwargs):
         self._choices = choices
         super(ChoicesField, self).__init__(**kwargs)

     def to_representation(self, obj):
         return self._choices[obj]

     def to_internal_value(self, data):
        return getattr(self._choices, data)


class QueueCreateSerializer(serializers.ModelSerializer):
    serviceType = ChoicesField(choices=servicesForCNAP.typeForServices, default=servicesForCNAP.typeForServices.Get)
    serviceName = ChoicesField(choices=servicesForCNAP.namesForServices,default=servicesForCNAP.namesForServices.Ратуша)
    class Meta:
        model = cnapWithService
        fields = ['znap','serviceType','date','status','serviceName']

    def create(self, validated_data):
        znap = validated_data['znap']
        queueObj = cnapWithService(
            znap=znap,
            status=False
        )
        queueObj.save()
        return validated_data



