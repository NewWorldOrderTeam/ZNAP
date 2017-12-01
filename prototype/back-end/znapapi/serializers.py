from rest_framework import serializers
from rest_framework.fields import CharField

from znapapi.models import Znap


class ZnapSerialezer(serializers.ModelSerializer):
    name = CharField(read_only=True)
    class Meta:
        model = Znap
        fields = ('id', 'name')

