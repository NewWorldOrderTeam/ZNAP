from django.forms import IntegerField, CharField
from rest_framework import serializers
from queueapi.models import cnapWithService, infoAboutCnap, servicesForCNAP
from django.contrib.auth.models import User

class QueueSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = infoAboutCnap
        fields = ('znap')

class QueueCreateSerializer(serializers.HyperlinkedModelSerializer):
    name = CharField()

    class Meta:
        model = infoAboutCnap
        fields = ['name']

    def create(self, validated_data):
        name = validated_data['name']
        queueObj = infoAboutCnap(
            name=name
        )
        queueObj.save()
        return validated_data

