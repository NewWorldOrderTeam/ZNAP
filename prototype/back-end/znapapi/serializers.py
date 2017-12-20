from django.contrib.auth.models import User
from rest_framework import serializers
from rest_framework.fields import CharField, IntegerField, DateField, TimeField

from znapapi.models import Znap, RegistrationToZnap


class ZnapSerialezer(serializers.ModelSerializer):
    name = CharField(read_only=True)
    class Meta:
        model = Znap
        fields = ('id', 'name')

class RegistrationToZnapSerializer(serializers.ModelSerializer):
    class Meta:
        model = RegistrationToZnap
        fields = ('id', 'user_id', 'znap_id', 'service', 'date', 'time')

class CreateRegistrationToZnapSerializer(serializers.HyperlinkedModelSerializer):
    user_id = IntegerField()
    date = DateField()
    time = TimeField()
    service = CharField()
    znap_id = IntegerField()
    class Meta:
        model = RegistrationToZnap
        fields = ('user_id', 'znap_id', 'service', 'date', 'time')

    def validate(self, data):
        user_id = data['user_id']
        user_qs = User.objects.filter(id=user_id)
        if not user_qs.exists():
            raise serializers.ValidationError("This user has not already registered")
        return data

    def create(self, validated_data):
        user_id = validated_data['user_id']
        znap_id = validated_data['znap_id']
        time = validated_data['time']
        date=validated_data['date']
        service = validated_data['service']
        reg_obj = RegistrationToZnap(
            user_id=user_id,
            znap_id = znap_id,
            time = time,
            date = date,
            service = service
        )
        reg_obj.save()

        return validated_data