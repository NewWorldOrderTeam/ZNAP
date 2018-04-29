from __future__ import unicode_literals

from django.contrib.auth.models import User
from django.db import models

# Create your models here.
from userapi.models import UserProfile


class Znap(models.Model):
    name = models.CharField(max_length=100)
    def __unicode__(self):
        return self.name

class RegistrationToZnap(models.Model):
    user = models.ForeignKey(UserProfile)
    znap = models.CharField(max_length=124)
    date = models.CharField(max_length=124)
    time = models.CharField(max_length=124)
    service = models.CharField(max_length=226)
    html = models.TextField()