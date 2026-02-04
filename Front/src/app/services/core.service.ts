import { Injectable, signal } from '@angular/core';
import { AppSettings, defaults } from '../config';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { urls } from '../components/const/urls';
import { Producto } from '../models/producto';
import Swal from 'sweetalert2';
import { Venta } from '../pages/ui-components/crud/grafico/venta';
import { CalendarEvent, CalendarEventSend } from '../models/events';

const AUTH_API = 'http://localhost:9096/api/auth/';
const API_CALENDAR_EVENTS = 'http://localhost:9096/calendar-event';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};
@Injectable({
  providedIn: 'root',
})
export class CoreService {
  private optionsSignal = signal<AppSettings>(defaults);
  baseUrl: string = urls.API_URL_TODOS;
  addUrl: string = urls.API_URL_TODOS_ADD;
  editUrl: string = urls.API_URL_TODOS_EDIT;
  deleteUrl: string = urls.API_URL_TODOS_DELETE;
  moneda: string = urls.API_URL_TODOS_MONEDA;
  private TOKEN_KEY = 'accessToken';
  dialogData!: Producto;

  private apiUrl = 'https://api.json2video.com/v1/render'; // ejemplo
  private apiKey = 'XoTtPDM2VxGcbBC5G89eLfaGPqNeJb2dwB1cEIch';
  constructor(private httpClient: HttpClient) { }
  getOptions() {
    return this.optionsSignal();
  }

  setOptions(options: Partial<AppSettings>) {
    this.optionsSignal.update((current) => ({
      ...current,
      ...options,
    }));
  }

  public getData = () => {
    return this.httpClient.get(this.baseUrl);
  }

  login(username: string, password: string): Observable<any> {
    console.log('Login data username:', username);
    console.log('Login data password:', password);
    console.log('API Endpoint:', AUTH_API);
    return this.httpClient.post(AUTH_API + 'signin', {
      username,
      password
    }, httpOptions);

  }

  // ADD, POST METHOD
  addItem(producto: Producto): void {
    const varSlice = producto.imagen.toString().slice(0, 50);
    console.log("Imagen en el servicio: " + varSlice);
    const modelProducto = {
      nombre: producto.nombre,
      descripcion: producto.descripcion,
      precio: producto.precio,
      cantidad: producto.cantidad,
      imagen: varSlice
    };

    console.log("Producto en el servicio: " + JSON.stringify(producto));
    console.log("Producto modificado en el servicio: " + JSON.stringify(modelProducto));
    this.httpClient.post(this.addUrl, modelProducto).subscribe(data => {
      this.dialogData = producto;
      Swal.fire(
        'Good job!',
        'You clicked the button!',
        'success'
      )
      this.httpClient.get(this.baseUrl);
    },
      (err: HttpErrorResponse) => {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Something went wrong!' + "\n" + err.message.toString,
          footer: '<a href="">Why do I have this issue?</a>'
        })
      });
  }

  // UPDATE, PUT METHOD
  updateItem(producto: Producto): void {
    const varSlice = producto.imagen.toString().slice(0, 50);
    console.log("Imagen en el servicio: " + varSlice);
    const modelProducto = {
      id: producto.id,
      nombre: producto.nombre,
      descripcion: producto.descripcion,
      precio: producto.precio,
      cantidad: producto.cantidad,
      imagen: varSlice
    };

    console.log("Producto en el servicio: " + JSON.stringify(producto));
    console.log("Producto modificado en el servicio: " + JSON.stringify(modelProducto));
    this.httpClient.post(this.editUrl, modelProducto).subscribe(data => {
      this.dialogData = producto;
      Swal.fire(
        'Good job!',
        'You clicked the button!',
        'success'
      )
      this.httpClient.get(this.baseUrl);
    },
      (err: HttpErrorResponse) => {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Something went wrong!' + "\n" + err.message.toString,
          footer: '<a href="">Why do I have this issue?</a>'
        })
      }
    );
  }

  // DELETE METHOD
  deleteItem(id: number): void {
    this.httpClient.delete(this.deleteUrl + id).subscribe(data => {
      //console.log(data['']);
      Swal.fire(
        'Good job!',
        'You clicked the button!',
        'success'
      )
    },
      (err: HttpErrorResponse) => {
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: 'Something went wrong!' + "\n" + err.message.toString,
          footer: '<a href="">Why do I have this issue?</a>'
        })
      }
    );
  }

  saveToken(token: string) {
    localStorage.setItem('token', token);
  }

  logout() {
    localStorage.removeItem('token');
  }

  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }
  obtenerVentas(): Observable<Venta> {
    return this.httpClient.get<Venta>(this.moneda);
  }

  // Store token securely
  public setToken(token: string): void {
    sessionStorage.setItem(this.TOKEN_KEY, token);
  }
  // Retrieve token
  public getToken(): string | null {
    return sessionStorage.getItem(this.TOKEN_KEY);
  }
  // Remove token
  public removeToken(): void {
    sessionStorage.removeItem(this.TOKEN_KEY);

  }

 /*  generarVideo(videoJson: any): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${this.apiKey}`
    });

    return this.httpClient.post(this.apiUrl, videoJson, { headers });
  } */
  generarVideo(videoJson: any): Observable<any> {
  return this.httpClient.post(
    'http://localhost:9096/api/auth/generar',
    videoJson
  );
}

 crearVideo(payload: any): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.apiKey}`,
      'Content-Type': 'application/json'
    });

    return this.httpClient.post(this.apiUrl, payload, { headers });
  }
  obtenerEstado(movieId: string): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.apiKey}`
    });

    return this.httpClient.get(`${this.apiUrl}/${movieId}`, { headers });
  }
   getEvents(): Observable<CalendarEvent[]> {
    return this.httpClient.get<CalendarEvent[]>(API_CALENDAR_EVENTS);
  }

  createEvent(event: CalendarEvent): Observable<CalendarEvent> {
    console.log('Creating event:', event);
    const dateTimeString: string = event.start;
    const dateObject: Date = new Date(dateTimeString);
    console.log('Converted Date object:', dateObject);
    const calentEvent: CalendarEventSend = {
      title: event.title,
      start: dateObject,
      end: dateObject,
      allDay: event.allDay
    };
    return this.httpClient.post<CalendarEvent>(API_CALENDAR_EVENTS, calentEvent);
  }

  updateEvent(id: number, event: CalendarEvent): Observable<CalendarEvent> {
    return this.httpClient.put<CalendarEvent>(`${API_CALENDAR_EVENTS}/${id}`, event);
  }

  deleteEvent(id: number): Observable<void> {
    return this.httpClient.delete<void>(`${API_CALENDAR_EVENTS}/${id}`);
  }

}
