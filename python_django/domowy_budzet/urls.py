from django.conf.urls import patterns, include, url
from django.contrib import admin
admin.autodiscover()

urlpatterns = patterns('',
    url(r'^admin/', include(admin.site.urls)),
    url(r'^/?$', 'uzytkownik.views.main'),
    url(r'^login/$', 'uzytkownik.views.login'),
    url(r'^auth/$', 'uzytkownik.views.auth_view'),
    url(r'^logout/$', 'uzytkownik.views.logout'),
    url(r'^loggedin/$', 'uzytkownik.views.loggedin'),
    url(r'^invalid/$', 'uzytkownik.views.invalid_login'),
    url(r'^register/$', 'uzytkownik.views.register_user'),
    url(r'^register_success/$', 'uzytkownik.views.register_success'),
    
    url(r'^create_r/$', 'uzytkownik.views.create_r'),
    url(r'^delete_r/(?P<pk>\d+)/$', 'uzytkownik.views.delete_r'),
    url(r'^rachunek_view/$', 'uzytkownik.views.rachunek_view'),
    
    url(r'^create_d/$', 'uzytkownik.views.create_d'),
    url(r'^delete_d/(?P<pk>\d+)/$', 'uzytkownik.views.delete_d'),
    url(r'^dochod_view/$', 'uzytkownik.views.dochod_view'),
    
    url(r'^przypomnienie_view/$', 'uzytkownik.views.przypomnienie_view'),
    url(r'^kategorie_view/$', 'uzytkownik.views.kategorie_view',name='kategorie'),
    url(r'^filtr_data/$', 'uzytkownik.views.filtrData_view',name='filtr_data'),
    url(r'^api/',include('RESTapi.urls')),
)
