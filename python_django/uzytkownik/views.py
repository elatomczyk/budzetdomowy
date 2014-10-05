from datetime import timedelta, datetime
from django.contrib import auth
from django.core.context_processors import csrf
from django.db.models import Sum
from django.http import HttpResponseRedirect
from django.shortcuts import render_to_response
from django.template import RequestContext
from forms import MyRegistrationForm
from forms import RachunkiForm, DochodyForm
from models import Rachunki, Dochody, Kategorie  

def main(request):
    return render_to_response('main.html')

def login(request):
    c = {}
    c.update(csrf(request))
    return render_to_response('uzytkownik/login.html', c)

def auth_view(request):
    username = request.POST.get('username', '')
    password = request.POST.get('password', '')
    user = auth.authenticate(username=username, password=password) 
    if user is not None:
        auth.login(request, user)
        return HttpResponseRedirect('/loggedin')
    else:
        return HttpResponseRedirect('/invalid')
    
def loggedin(request):
    uzytkownik = request.user
    ob_dochody = Dochody.objects.filter(id_u=uzytkownik.id)
    ob_rachunki = Rachunki.objects.filter(id_u=uzytkownik.id)
    sumaD = 0
    sumaR = 0
    for dochod in ob_dochody:
        sumaD = sumaD + dochod.cena
    for rachunki in ob_rachunki:
        sumaR = sumaR + rachunki.cena
    wynik = sumaD - sumaR
    return render_to_response('uzytkownik/loggedin.html',
                              {'suma_d':sumaD, 'suma_r':sumaR, 'minus': wynik},
                              context_instance=RequestContext(request))

def invalid_login(request):
    return render_to_response('uzytkownik/invalid_login.html')

def logout(request):
    auth.logout(request)
    return render_to_response('uzytkownik/logout.html')

def register_user(request):
    if request.method == 'POST':
        form = MyRegistrationForm(request.POST)
        if form.is_valid():
            form.save()
            return HttpResponseRedirect('/register_success')
        
    args = {}
    args.update(csrf(request))
    
    args['form'] = MyRegistrationForm()
    print args
    return render_to_response('uzytkownik/register.html', args)

def register_success(request):
    return render_to_response('uzytkownik/register_success.html')

def rachunek_view(request):
    uzytkownik = request.user
    rachunki = Rachunki.objects.filter(id_u=uzytkownik.id)
    suma_r = Rachunki.objects.filter(id_u=uzytkownik.id).aggregate(suma_r=Sum('cena'))
    return render_to_response('rachunek/rachunek_view.html',
                              {'rachunki': rachunki, 'suma':suma_r},
                              context_instance=RequestContext(request))

def przypomnienie_view(request):
    uzytkownik = request.user
    today = datetime.today().date()
    seven_days_ago = today + timedelta(days=7)
    rachunki = Rachunki.objects.filter(id_u=uzytkownik.id, powiadomienie=True).order_by('data_zaplaty')
    return render_to_response('rachunek/przypomnienie_view.html',
                              {'rachunki': rachunki, 'today':today, 'seven_days_ago':seven_days_ago},
                              context_instance=RequestContext(request))
    
def kategorie_view(request):
    zapisane = Kategorie.objects.all()
    if request.POST:
        uzytkownik = request.user
        zapisane1 = Rachunki.objects.filter(id_u=uzytkownik.id, id_k=request.POST.get('choice', False))
    else:
        zapisane1 = 0
    return render_to_response('rachunek/kategorie.html',
                              {'kategoria': zapisane, 'rachunki': zapisane1 },
                              context_instance=RequestContext(request)) 
    
def filtrData_view(request):
    
    dzien = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"]
    miesiac = ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"]
    rok = ["2014", "2013"]
    
    if request.POST:
        uzytkownik = request.user
    
        data_start = request.POST.get('r', False) + "-" + request.POST.get('m', False) + "-" + request.POST.get('d', False)
        data_koniec = request.POST.get('r2', False) + "-" + request.POST.get('m2', False) + "-" + request.POST.get('d2', False)
        zapisane1 = Rachunki.objects.filter(id_u=uzytkownik.id, data_zaplaty__gte=data_start, data_zaplaty__lte=data_koniec)
        
    else:
        zapisane1 = 0
    return render_to_response('rachunek/filtrowanie_po_dacie.html',
                              {'dzien': dzien, 'miesiac': miesiac , 'rok':rok, 'rachunki': zapisane1 },
                              context_instance=RequestContext(request)) 

def create_r(request):
    form = RachunkiForm(request.POST or None)
    uzytkownik = request.user
    if form.is_valid():
        save_it = form.save(commit=False)
        save_it.id_u = uzytkownik
        save_it.save()     
    return render_to_response('rachunek/create_rachunki.html',
                              locals(),
                              context_instance=RequestContext(request))

def delete_r(request, pk):
    do_usuniecia = Rachunki.objects.filter(id=pk)
    do_usuniecia.delete()
    return HttpResponseRedirect('/rachunek_view') 
     
def dochod_view(request):
    uzytkownik = request.user
    dochody = Dochody.objects.filter(id_u=uzytkownik.id)
    suma_d = Dochody.objects.filter(id_u=uzytkownik.id).aggregate(suma_d=Sum('cena'))   
    return render_to_response('dochod/dochod_view.html',
                              {'dochody': dochody, 'suma':suma_d},
                              context_instance=RequestContext(request))

def create_d(request):
    form = DochodyForm(request.POST or None)
    uzytkownik = request.user
    if form.is_valid():
        save_it = form.save(commit=False)
        save_it.id_u = uzytkownik
        save_it.save()     
    return render_to_response('dochod/create_dochody.html',
                              locals(),
                              context_instance=RequestContext(request))

def delete_d(request, pk):
    do_usuniecia = Dochody.objects.filter(id=pk)
    do_usuniecia.delete()
    return HttpResponseRedirect('/dochod_view')
