from django.forms import IntegerField, CharField
from rest_framework import serializers
from queueapi.models import cnapWithService, infoAboutCnap, servicesForCNAP
from django.contrib.auth.models import User

class QueueSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = infoAboutCnap,servicesForCNAP
        fields = ('znap','serviceType')

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
    class Meta:
        model = infoAboutCnap
        fields = ['name','serviceType']

    def create(self, validated_data):
        name = validated_data['name']

        queueObj = infoAboutCnap(
            name=name
        )
        queueObj.save()
        return validated_data

