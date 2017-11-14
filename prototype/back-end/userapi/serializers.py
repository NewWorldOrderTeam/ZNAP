from django.contrib.auth.models import User
from rest_framework import serializers
from rest_framework.authtoken.models import Token
from rest_framework.fields import CharField, EmailField, IntegerField

from userapi.models import UserProfile


class UserSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = UserProfile
        fields = ('url', 'id', 'first_name', 'last_name', 'middle_name', 'email', 'phone')

class UserCreateSerializer(serializers.HyperlinkedModelSerializer):
    first_name = CharField()
    last_name = CharField()
    middle_name = CharField()
    email = EmailField(label='Email')
    phone = CharField()
    class Meta:
        model = UserProfile
        fields = ['id','first_name', 'last_name', 'middle_name', 'email', 'password', 'phone']
        extra_kwargs = {"password" : {"write_only": True}}

    def validate(self, data):
        email = data['email']
        user_qs = UserProfile.objects.filter(email=email)
        if user_qs.exists():
            raise serializers.ValidationError("This user has already registered")
        return data

    def create(self, validated_data):
        first_name = validated_data['first_name']
        last_name = validated_data['last_name']
        middle_name = validated_data['middle_name']
        email = validated_data['email']
        phone = validated_data['phone']
        password = validated_data['password']
        user_obj = UserProfile(
            username = email,
            first_name = first_name,
            last_name = last_name,
            middle_name = middle_name,
            email = email,
            phone=phone
        )
        user_obj.set_password(password)
        user_obj.save()
        return validated_data

class UserLoginSerializer(serializers.ModelSerializer):
    email = EmailField(label= 'Email', write_only=True)
    class Meta:
        model = UserProfile
        fields = ['id', 'email', 'password']
        extra_kwargs = {"password" : {"write_only": True}}

    def validate(self, data):
        user_obj = None
        email = data.get("email", None)
        password = data['password']
        user = UserProfile.objects.filter(email = email)
        user = user.exclude(email__isnull = True).exclude(email__iexact = '')
        if user.exists() and user.count()==1:
            user_obj = user.first()
            data['id']=user_obj.id
        else:
            raise serializers.ValidationError("This email is not valid")
        if user_obj:
            if not user_obj.check_password(password):
                raise serializers.ValidationError("Incorrect password")
        return data

