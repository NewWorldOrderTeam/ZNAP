# -*- coding: utf-8 -*-
# Generated by Django 1.11.6 on 2017-11-02 11:16
from __future__ import unicode_literals

import datetime
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('userapi', '0027_auto_20171102_1305'),
    ]

    operations = [
        migrations.AlterField(
            model_name='dialog',
            name='timeStamp',
            field=models.DateTimeField(default=datetime.datetime(2017, 11, 2, 13, 16, 38, 50000)),
        ),
    ]
