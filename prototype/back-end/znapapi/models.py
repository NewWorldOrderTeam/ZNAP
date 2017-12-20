from __future__ import unicode_literals

from django.db import models

# Create your models here.
from userapi.models import UserProfile


class Znap(models.Model):
    name = models.CharField(max_length=100)
    def __unicode__(self):
        return self.name

class RegistrationToZnap(models.Model):
    user = models.ForeignKey(UserProfile)
    znap = models.ForeignKey(Znap)
    date = models.CharField(max_length=124)
    time = models.CharField(max_length=124)
    service = models.CharField(max_length=226)


