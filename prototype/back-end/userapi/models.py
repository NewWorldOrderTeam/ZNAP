from __future__ import unicode_literals
from django.contrib.auth.models import User
from django.db import models
from model_utils import Choices
import datetime

# Create your models here


class Rate(models.Model):
    user = models.ForeignKey(User)
    description = models.CharField(default=None, max_length=228)
    QUALITY = Choices(
        ('Good', 'G'),
        ('Bad', 'B'),
    )

    quality = models.CharField(max_length=1, choices=QUALITY, default=QUALITY.Good)

class Admin(models.Model):
    email = models.EmailField()
    password = models.CharField(max_length=50)
    def __str__(self):
        return str(self.email)

class Dialog(models.Model):
    user = models.ForeignKey(User)
    admin = models.ForeignKey(Admin,default=None, null=True)
    message = models.CharField(max_length=200)
    type = Choices(
        ('1','1')
    )
    dialogID = models.IntegerField()
    timeStamp = models.DateTimeField(default=datetime.datetime.now())
    typef = models.CharField(max_length=1, choices=type)

