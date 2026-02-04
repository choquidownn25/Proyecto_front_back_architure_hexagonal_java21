import { CommonModule } from '@angular/common';
import { Component, OnInit, TemplateRef, ViewChild, forwardRef } from '@angular/core';
import { CalendarOptions, Calendar, EventClickArg } from '@fullcalendar/core';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin, { DateClickArg, EventDragStopArg } from '@fullcalendar/interaction';
import { FullCalendarComponent, FullCalendarModule } from '@fullcalendar/angular';

@Component({
  selector: 'app-fullcalendar',
  standalone: true,
  imports: [CommonModule,
    FullCalendarModule],
  templateUrl: 'calendar-full.html',
})

export class AppCalendarComponent {

  @ViewChild('calendar') calendarComponent!: FullCalendarComponent;

  calendarOptions: CalendarOptions = {
    plugins: [dayGridPlugin, interactionPlugin],
    initialView: 'dayGridMonth',

    events: [
      { title: 'Evento inicial', date: new Date() }
    ],
    // 👉 Bloquea fechas pasadas


    // 👉 Aquí ocurre la magia
    dateClick: (info) => this.createEvent(info.dateStr)
  };

  createEvent(dateStr: string) {
    const clickedDate = new Date(dateStr);
    const today = new Date();

    // comparar solo fecha, no hora
    today.setHours(0, 0, 0, 0);
    clickedDate.setHours(0, 0, 0, 0);

    if (clickedDate < today) {
      alert("No puedes crear eventos en fechas pasadas");
      return;
    }
    const calendarApi = this.calendarComponent.getApi();

    calendarApi.addEvent({
      title: 'Nuevo evento',
      start: dateStr,
      allDay: true
    });
  }


}



