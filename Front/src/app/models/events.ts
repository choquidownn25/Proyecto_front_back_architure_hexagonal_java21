import { D } from "node_modules/@fullcalendar/core/internal-common";

export interface CalendarEvent {
  id?: number;
  title: string;
  start: string;
  end: string;
  allDay: boolean;

}
export interface CalendarEventSend {
  id?: number;
  title: string;
  start: Date;
  end: Date;
  allDay: boolean;

}
