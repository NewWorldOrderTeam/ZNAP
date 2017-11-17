import datetime
from django.forms import CharField, DateField, DateTimeField, BooleanField
from rest_framework import serializers
from queueapi.models import infoAboutCnap, servicesForCNAP, cnapWithService
from znap import settings


class QueueSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = infoAboutCnap,servicesForCNAP,cnapWithService
        fields = ('znap','serviceType','date','status')

class ChoicesField(serializers.Field):
     def __init__(self, choices, **kwargs):
         self._choices = choices
         super(ChoicesField, self).__init__(**kwargs)

     def to_representation(self, obj):
         return self._choices[obj]

     def to_internal_value(self, data):
        return getattr(self._choices, data)


class QueueCreateSerializer(serializers.HyperlinkedModelSerializer):
    name = CharField()
    serviceType = ChoicesField(choices=servicesForCNAP.CHOICES, default=servicesForCNAP.CHOICES.Get)
    date = DateTimeField()
    status = BooleanField()

    class Meta:
        model = infoAboutCnap
        fields = ['name','serviceType','date','status']

    def create(self, validated_data):
        name = validated_data['name']
        date = validated_data['date']


        queueObj = infoAboutCnap(
            name=name,
            date = date,
            status=False
        )
        queueObj.save()
        return validated_data

