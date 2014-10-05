from django.conf.urls import patterns, include, url
from RESTapi import views
from rest_framework.urlpatterns import format_suffix_patterns

urlpatterns = patterns('',
    url(r'^dochody/(?P<nazwa>[0-9a-zA-Z _-]+)/$', views.DochodyLista.as_view(), name='ListaDochodow'),
    url(r'^rachunki/(?P<nazwa>[0-9a-zA-Z _-]+)/$', views.RachunkiLista.as_view(), name='ListaRachunkow'),
    url(r'^kategorie/$', views.KategorieLista.as_view(), name='Lista Kategorii'),
    url(r'^d/(?P<pk>\d+)/$', views.DochodyDetail.as_view()),
    url(r'^r/(?P<pk>\d+)/$', views.RachunkiDetail.as_view()),
    url(r'^usunr/(?P<pk>\d+)/$', views.UsuwanieRachunki.as_view()),
    url(r'^usund/(?P<pk>\d+)/$', views.UsuwanieDochody.as_view()),
    url(r'^dodajr/(?P<tytull>[0-9a-zA-Z _-]+)/(?P<opiss>[0-9a-zA-Z _-]+)/(?P<cenaa>[0-9a-zA-Z _-]+)/(?P<powiadomieniee>[0-9a-zA-Z _-]+)/(?P<id_kk>[0-9a-zA-Z _-]+)/(?P<id_uu>[0-9a-zA-Z _-]+)/$', views.DodawanieRachunku.as_view()),
    url(r'^dodajd/(?P<tytull>[0-9a-zA-Z _-]+)/(?P<opiss>[0-9a-zA-Z _-]+)/(?P<cenaa>[0-9a-zA-Z _-]+)/(?P<id_uu>[0-9a-zA-Z _-]+)/$', views.DodawanieDochodu.as_view()),
    url(r'^auth/', include('rest_framework.urls', namespace='rest_framework')),
    url(r'^login/(?P<nazwa>[0-9a-zA-Z _-]+)/(?P<haslo>[0-9a-zA-Z _-]+)/$', views.Login.as_view()),
)
urlpatterns = format_suffix_patterns(urlpatterns) 
