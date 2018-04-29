from __future__ import unicode_literals

from django.contrib.auth.models import User
from django.db import models

# Create your models here.
from adminapi.models import Admin

class Chat(models.Model):
    user = models.ForeignKey(User)
    admin = models.ForeignKey(Admin,default=None, null=True)
    is_closed = models.BooleanField(default=False)
    timestamp = models.DateTimeField(auto_now_add=True)

class Message(models.Model):
    chat = models.ForeignKey(Chat)
    message = models.CharField(max_length=1200)
    is_read = models.BooleanField(default=False)
    is_admin = models.BooleanField(default=False)
    timestamp = models.DateTimeField(auto_now_add=True)

