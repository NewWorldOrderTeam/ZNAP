# -*- coding: utf-8 -*-
# Generated by Django 1.10.3 on 2017-12-06 16:41
from __future__ import unicode_literals

import datetime
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('rateapi', '0049_auto_20171201_1400'),
    ]

    operations = [
        migrations.AlterField(
            model_name='dialog',
            name='timeStamp',
            field=models.DateTimeField(default=datetime.datetime(2017, 12, 6, 18, 41, 44, 468000)),
        ),
    ]
