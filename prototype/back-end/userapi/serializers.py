from django.contrib.auth import get_user_model
from django.contrib.auth.models import User
from rest_framework import serializers
from rest_framework.authtoken.models import Token
from rest_framework.fields import CharField, EmailField, SerializerMethodField, IntegerField, BooleanField
from rest_framework.test import APIRequestFactory

from userapi.models import Admin, Dialog, Rate, UserProfile




factory = APIRequestFactory()
request = factory.get('/')

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
        fields = ['first_name', 'last_name', 'middle_name', 'email', 'password', 'phone']
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
    email = EmailField(label= 'Email')
    class Meta:
        model = UserProfile
        fields = ['email', 'password',]
        extra_kwargs = {"password" : {"write_only": True}}

    def validate(self, data):
        user_obj = None
        email = data.get("email", None)
        password = data['password']
        user = UserProfile.objects.filter(email = email)
        user = user.exclude(email__isnull = True).exclude(email__iexact = '')
        if user.exists() and user.count()==1:
            user_obj = user.first()
        else:
            raise serializers.ValidationError("This email is not valid")
        if user_obj:
            if not user_obj.check_password(password):
                raise serializers.ValidationError("Incorrect password")
        return data

class RateSerializer(serializers.ModelSerializer):
    dialog = SerializerMethodField()
    class Meta:
        model = Rate
        fields = ('user_id', 'admin_id', 'quality', 'description', 'is_closed', 'dialog' )

    def get_dialog(self, obj):
        d_qs = Dialog.objects.filter(dialog_id=obj.id)
        dialog = DialogSerializer(d_qs, many=True, context={'request': request}).data
        return dialog

class ChoicesField(serializers.Field):
    def __init__(self, choices, **kwargs):
        self._choices = choices
        super(ChoicesField, self).__init__(**kwargs)

    def to_representation(self, obj):
        return self._choices[obj]

    def to_internal_value(self, data):
        return getattr(self._choices, data)


class RateCreateSerializer(serializers.HyperlinkedModelSerializer):
    user_id = IntegerField()
    description = CharField(allow_blank=True)
    quality = IntegerField()
    class Meta:
        model = Rate
        fields = ('user_id', 'description','quality')

    def validate(self, data):
        user_id = data['user_id']
        user_qs = User.objects.filter(id=user_id)
        if not user_qs.exists():
            raise serializers.ValidationError("This user has not already registered")
        return data

    def create(self, validated_data):
        user_id = validated_data['user_id']
        description = validated_data['description']
        quality=validated_data['quality']
        rate_obj = Rate(
            user_id=user_id,
            quality=quality,
            description=description,
            is_closed=False
        )
        rate_obj.save()

        chat_obj = Dialog(
            dialog_id=rate_obj.id,
            message=description
        )

        chat_obj.save()
        return validated_data

class AdminLoginSerializer(serializers.ModelSerializer):
    email = EmailField(label= 'Email')
    class Meta:
        model = Admin
        fields = ['email', 'password',]
        extra_kwargs = {"password" : {"write_only": True}}

    def validate(self, data):
        admin_obj = None
        email = data.get("email", None)
        password = data['password']
        admin = Admin.objects.filter(email = email)
        admin = admin.exclude(email__isnull = True).exclude(email__iexact = '')
        if admin.exists() and admin.count()==1:
            admin_obj = admin.first()
        else:
            raise serializers.ValidationError("This email is not valid")
        if admin_obj:
            if (password!=admin_obj.password):
                raise serializers.ValidationError("Incorrect password")
        return data


class DialogSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Dialog
        fields = ('message', 'timeStamp')