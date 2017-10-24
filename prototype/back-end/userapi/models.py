from __future__ import unicode_literals

from django.contrib.auth.models import User
from django.db import models

# Create your models here


class Rate(models.Model):
    user = models.ForeignKey(User)
    rate = models.IntegerField()
    description = models.CharField(default=None, max_length=228)
    timeStamp = models.BooleanField()

