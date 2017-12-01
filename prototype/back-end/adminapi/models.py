from __future__ import unicode_literals

from django.db import models

# Create your models here.
from model_utils import Choices

from znapapi.models import Znap


class Admin(models.Model):
    znap = models.ForeignKey(Znap)
    email = models.EmailField()
    password = models.CharField(max_length=50)
    def __str__(self):
        return str(self.email)
