from rest_framework import serializers
from queueapi.models import servicesForCNAP, cnapWithService


class QueueSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = servicesForCNAP
        fields = ('nameOfZnap','serviceType','date','service')

class ChoicesField(serializers.Field):
     def __init__(self, choices, **kwargs):
         self._choices = choices
         super(ChoicesField, self).__init__(**kwargs)

     def to_representation(self, obj):
         return self._choices[obj]

     def to_internal_value(self, data):
        return getattr(self._choices, data)


class QueueCreateSerializer(serializers.ModelSerializer):
    serviceType = ChoicesField(choices=servicesForCNAP.typeForServices, default=servicesForCNAP.typeForServices.Post)
    serviceName = ChoicesField(choices=servicesForCNAP.namesForServices,default=servicesForCNAP.namesForServices.ОтриматиПаспорт)
    class Meta:
        model = cnapWithService
        fields = ['nameOfZnap','serviceType','date','status','serviceName']

    def create(self, validated_data):
        nameOfZnap = validated_data['nameOfZnap']
        queueObj = cnapWithService(
            nameOfZnap=nameOfZnap,
            status=False
        )
        queueObj.save()
        return validated_data