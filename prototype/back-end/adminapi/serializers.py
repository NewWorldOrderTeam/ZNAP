from rest_framework import serializers
from rest_framework.authtoken.models import Token
from rest_framework.fields import EmailField

from adminapi.models import Admin


class AdminLoginSerializer(serializers.ModelSerializer):
    email = EmailField(label= 'Email', write_only=True)
    class Meta:
        model = Admin
        fields = ['id', 'email', 'password',]
        extra_kwargs = {"password" : {"write_only": True}}

    def validate(self, data):
        admin_obj = None
        email = data.get("email", None)
        password = data['password']
        admin = Admin.objects.filter(email = email)
        admin = admin.exclude(email__isnull = True).exclude(email__iexact = '')
        if admin.exists() and admin.count()==1:
            admin_obj = admin.first()
            data['id'] = admin_obj.id
        else:
            raise serializers.ValidationError("This email is not valid")
        if admin_obj:
            if (password!=admin_obj.password):
                raise serializers.ValidationError("Incorrect password")
        return data


class AdminSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Admin
        fields = ('url', 'id', 'email')