import { Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { FullCalendarComponent, FullCalendarModule } from '@fullcalendar/angular';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import { CalendarOptions, EventDropArg } from '@fullcalendar/core';
import Swal from 'sweetalert2';
import timeGridPlugin from '@fullcalendar/timegrid';


@Component({
  selector: 'app-calendar',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    FullCalendarModule
  ],
  templateUrl: 'dialog-calendar-full.html',
  styleUrls: ['dialog-calendar-full.css']
})
export class DialogCalendarComponent {


  // Referencia al calendario
  @ViewChild('calendar') calendarComponent!: FullCalendarComponent;

  // Popup control
  showPopup = false;

  // Form data
  selectedDate = '';
  eventTitle = '';
  eventTime = '';

  calendarOptions: CalendarOptions = {
    plugins: [dayGridPlugin, interactionPlugin],
    initialView: 'dayGridMonth',
    editable: true, // Permite drag & drop
    eventTimeFormat: {
      hour: '2-digit',
      minute: '2-digit',
      hour12: true   // false si quieres 24h
    },

    displayEventEnd: false,
    events: [
      { title: 'Evento inicial', date: new Date() }
    ],

    dateClick: (info) => this.openPopup(info.dateStr),
    // Validar al mover evento
    eventDrop: (info) => this.validateDrop(info)
  };
  eventDate: any;
  validateDrop(info: any) {

    const today = new Date();
    today.setHours(0, 0, 0, 0);

    const newDate = new Date(info.event.start);
    newDate.setHours(0, 0, 0, 0);

    if (newDate < today) {

      Swal.fire({
        icon: "error",
        title: "Error...",
        text: "No puedes mover eventos a fechas pasadas!",
        footer: '<a href="#">Por que el problema?</a>'
      });
      info.revert(); // regresa a su fecha original
    }
  }

  // Abrir popup
  openPopup(dateStr: string) {
    const clicked = new Date(dateStr);
    const today = new Date();

    today.setHours(0, 0, 0, 0);
    clicked.setHours(0, 0, 0, 0);

    if (clicked < today) {
      ///alert('No puedes crear eventos en el pasado');
      Swal.fire({
        icon: "error",
        title: "Error...",
        text: "No puedes crear eventos en fechas pasadas!",
        footer: '<a href="#">Por que el problema?</a>'
      });
      return;
    }

    this.selectedDate = dateStr;
    this.showPopup = true;
  }

  // Guardar evento
  saveEvent() {
    if (!this.eventTitle.trim()) {
      Swal.fire({
        icon: 'warning',
        title: 'Titulo del evento requerida',
        text: 'Debes ingresar un título para el evento'
      });
      return;
    }
    if (!this.eventTime) {
      Swal.fire({
        icon: 'warning',
        title: 'Hora requerida',
        text: 'Debes seleccionar una hora'
      });
      return;
    }
    const api = this.calendarComponent.getApi();
    // 👉 Fecha + hora en formato ISO
    const startDateTime = `${this.selectedDate}T${this.eventTime}`;

    api.addEvent({
      title: this.eventTitle,
      start: startDateTime,
      //time: this.eventTime,
      allDay: false

    });

    this.closePopup();
  }

  // Cerrar popup
  closePopup() {
    this.showPopup = false;
    this.eventTitle = '';
    this.eventTime = '';
  }

}
