from __future__ import unicode_literals

from django.db import models

# Create your models here.
from model_utils import Choices


class Admin(models.Model):
    email = models.EmailField()
    password = models.CharField(max_length=50)
    def __str__(self):
        return str(self.email)
