from __future__ import unicode_literals
from django.contrib.auth.models import User
from django.db import models
from model_utils import Choices
import datetime

# Create your models here


class Rate(models.Model):
    user = models.ForeignKey(User)
    rate = models.IntegerField()
    description = models.CharField(default=None, max_length=228)
    timeStamp = models.DateTimeField(default=datetime.datetime.now())
    QUALITY = Choices(
        ('Good', 'G'),
        ('Bad', 'B'),
    )

    quality = models.CharField(max_length=1, choices=QUALITY, default=QUALITY.Good)