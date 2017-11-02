from django.contrib import admin

# Register your models here.
from django.contrib.auth.models import User

from userapi.models import Admin

admin.site.register(Admin)

admin.site.unregister(User)