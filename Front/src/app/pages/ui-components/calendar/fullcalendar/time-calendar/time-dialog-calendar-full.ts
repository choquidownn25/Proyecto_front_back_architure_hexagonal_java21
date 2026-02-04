import { Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { FullCalendarComponent, FullCalendarModule } from '@fullcalendar/angular';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import { CalendarOptions, EventApi, EventDropArg } from '@fullcalendar/core';
import Swal from 'sweetalert2';
import timeGridPlugin from '@fullcalendar/timegrid';
import { CoreService } from 'src/app/services/core.service';

const INITIAL_EVENTS = [];

const createEventId = () => {
  return String(Math.random());
};


@Component({
  selector: 'app-calendar',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    FullCalendarModule
  ],
  templateUrl: 'time-dialog-calendar-full.html',
  // styleUrls: ['../../calendar/dialog-calendar-full.css']
})
export class TimeDialogCalendarComponent {

  calendarVisible = true;
  // Referencia al calendario
  @ViewChild('calendar') calendarComponent!: FullCalendarComponent;

  // Popup control
  showPopup = false;

  // Form data
  selectedDate = '';
  eventTitle = '';
  eventTime = '';
  constructor(private calendarService: CoreService) { }

  calendarOptions: CalendarOptions = {
    plugins: [dayGridPlugin, interactionPlugin, timeGridPlugin],
    headerToolbar: {
      left: 'prev,next today',
      center: 'title',
      right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
    },
    initialView: 'dayGridMonth',
    //initialEvents: INITIAL_EVENTS,
    weekends: true,
    editable: true,
    selectable: true,
    selectMirror: true,
    dayMaxEvents: true,


    //select: this.handleDateSelect.bind(this),
    eventClick: this.handleEventClick.bind(this),
    eventsSet: this.handleEvents.bind(this),
    eventTimeFormat: {
      hour: '2-digit',
      minute: '2-digit',
      hour12: true
    },
    displayEventEnd: false,
    events: [
      { title: 'Evento inicial', date: new Date() }
    ],
    dateClick: (info: { dateStr: string }) => this.openPopup(info.dateStr),
    eventDrop: (info: EventDropArg) => this.validateDrop(info)
  };

  currentEvents: EventApi[] = [];
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
        text: "No puedes crear eventos en fechas pasadas!"
      });
      return;
    }

    this.selectedDate = dateStr;
    this.showPopup = true;
  }

  // Guardar evento
  // saveEvent() {
  //   if (!this.eventTitle.trim()) {
  //     Swal.fire({
  //       icon: 'warning',
  //       title: 'Titulo del evento requerida',
  //       text: 'Debes ingresar un título para el evento'
  //     });
  //     return;
  //   }
  //   if (!this.eventTime) {
  //     Swal.fire({
  //       icon: 'warning',
  //       title: 'Hora requerida',
  //       text: 'Debes seleccionar una hora'
  //     });
  //     return;
  //   }
  //   const api = this.calendarComponent.getApi();
  //   // 👉 Fecha + hora en formato ISO
  //   const startDateTime = `${this.selectedDate}T${this.eventTime}`;/*
  //   // Calculate end time (1 hour after start)
  //   const startDate = new Date(startDateTime);
  //   const endDate = new Date(startDate.getTime() + 60 * 60 * 1000);
  //   const endDateTime = endDate.toISOString().slice(0, 16).replace('T', 'T');

  //   api.addEvent({
  //     title: this.eventTitle,
  //     start: startDateTime,
  //     end: endDateTime,
  //     //time: this.eventTime,
  //     allDay: false
  //   }); */
  //   // 👉 Construcción SEGURA de fecha + hora
  // const startDate = new Date(`${this.selectedDate}T${this.eventTime}:00`);

  // if (isNaN(startDate.getTime())) {
  //   console.error('Fecha inválida:', startDate);
  //   return;
  // }

  // const endDate = new Date(startDate);
  // endDate.setHours(endDate.getHours() + 1);

  // api.addEvent({
  //   title: this.eventTitle,
  //   start: startDate,
  //   end: endDate,
  //   allDay: false
  // });

  //   this.closePopup();
  // }
  saveEvent() {
    if (!this.eventTitle.trim()) {
      Swal.fire({
        icon: 'warning',
        title: 'Título requerido',
        text: 'Debes ingresar un título'
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

    // 🧠 separar hora y minutos
    const [hours, minutes] = this.eventTime.split(':').map(Number);

    const startDate = new Date(this.selectedDate);
    startDate.setHours(hours, minutes, 0, 0);

    const endDate = new Date(startDate);
    endDate.setHours(endDate.getHours() + 1);

    const newEvent = {
      title: this.eventTitle,
      start: startDate.toISOString(),
      end: endDate.toISOString(),
      allDay: false,
      color: 'event-blue'
    };

    this.calendarService.createEvent(newEvent).subscribe(saved => {
      this.calendarComponent.getApi().addEvent({
        id: String(saved.id),
        title: saved.title,
        start: saved.start,
        end: saved.end,
        allDay: saved.allDay,
        classNames: 'event-blue'
      });

      //Adding event to calendar UI
      api.addEvent({
        title: this.eventTitle,
        start: startDate,
        end: endDate,
        allDay: false
      });

    }, error => {
      console.error('Error saving event:', error);
      Swal.fire({
        icon: 'error',
        title: 'Error',
        text: 'Hubo un problema al guardar el evento'
      });
    });



    this.closePopup();
  }

  // Cerrar popup
  closePopup() {
    this.showPopup = false;
    this.eventTitle = '';
    this.eventTime = '';
  }


  handleEventClick(clickInfo: any) {
    // Handle event click
    Swal.fire({
      title: "Are you sure?",
      text: "You won't be able to revert this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonColor: "#3085d6",
      cancelButtonColor: "#d33",
      confirmButtonText: "Yes, delete it!"
    }).then((result) => {
      if (result.isConfirmed) {
        clickInfo.event.remove();
        Swal.fire({
          title: "Deleted!",
          text: "Your file has been deleted.",
          icon: "success"
        });
      }
    });

  }

  handleEvents(events: any) {
    // Handle events set
    this.currentEvents = events;
  }

  handleCalendarToggle() {
    this.calendarVisible = !this.calendarVisible;
  }

  handleWeekendsToggle() {
    const { calendarOptions } = this;
    calendarOptions.weekends = !calendarOptions.weekends;
  }
}
