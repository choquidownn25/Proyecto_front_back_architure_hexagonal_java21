import { Component, OnInit, ViewChild } from '@angular/core';
import { NgApexchartsModule, ChartComponent } from 'ng-apexcharts';
import {
  ApexAxisChartSeries,
  ApexChart,
  ApexDataLabels,
  ApexPlotOptions,
  ApexYAxis,
  ApexTitleSubtitle,
  ApexXAxis,
  ApexFill
} from 'ng-apexcharts';
import { CoreService } from 'src/app/services/core.service';
import { Venta } from '../grafico/venta';

export interface ChartOptions {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  dataLabels: ApexDataLabels;
  plotOptions: ApexPlotOptions;
  yaxis: ApexYAxis;
  xaxis: ApexXAxis;
  fill: ApexFill;
  title: ApexTitleSubtitle;
}
@Component({
  selector: 'app-grafic-bar',
  imports: [ChartComponent,
    NgApexchartsModule,

  ],
  templateUrl: './grafic-bar.component.html',
  styleUrl: './grafic-bar.component.scss'
})
export class GraficBarComponent implements OnInit {

  @ViewChild('chart') chart!: ChartComponent;

  videoUrl?: string;
  cargando = false;

  movieId?: string;
  public chartOptions: ChartOptions = {
    series: [
      /*  {
         name: 'Inflation',
         data: [2.3, 3.1, 4.0, 10.1, 4.0, 3.6, 3.2, 2.3, 1.4, 0.8, 0.5, 0.2]
       } */
    ],
    chart: {
      type: 'bar',
      height: 350
    },
    plotOptions: {
      bar: {
        dataLabels: {
          position: 'top'
        }
      }
    },
    dataLabels: {
      enabled: true,
      formatter: (val: number) => `${val}%`,
      offsetY: -20,
      style: {
        fontSize: '12px',
        colors: ['#304758']
      }
    },
    xaxis: {
      categories: [
        // 'Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
        // 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'
      ],
      position: 'top',
      labels: {
        offsetY: -18
      },
      axisBorder: {
        show: false
      },
      axisTicks: {
        show: false
      },
      tooltip: {
        enabled: true,
        offsetY: -35
      }
    },
    fill: {
      type: 'gradient',
      gradient: {
        shade: 'light',
        type: 'horizontal',
        shadeIntensity: 0.25,
        inverseColors: true,
        opacityFrom: 1,
        opacityTo: 1,
        stops: [50, 0, 100]
      }
    },
    yaxis: {
      axisBorder: {
        show: false
      },
      axisTicks: {
        show: false
      },
      labels: {
        show: false,
        formatter: (val: number) => `${val}%`
      }
    },
    title: {
      text: 'Money',
      floating: true,
      offsetY: 0,
      align: 'center',
      style: {
        color: '#444'
      }
    }
  };

  constructor(private ventasService: CoreService) {

  }
  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos(): void {
    this.ventasService.obtenerVentas().subscribe(
      (venta: Venta) => {
        const monedas = ['usd', 'cop', 'gbp'];
        this.chartOptions.series = [
          {
            name: 'EUR',
            data: monedas.map(m => venta.eur[m])
          }
        ];

        this.chartOptions.xaxis = {
          categories: monedas.map(m => m.toUpperCase())
        };
      });
  }

  crearVideo() {
    this.cargando = true;

    const videoJson = {
      resolution: '1080p',
      fps: 30,
      scenes: [
        {
          background: '#1e293b',
          duration: 3,
          elements: [
            {
              type: 'text',
              text: 'Hola desde Angular',
              x: 'center',
              y: 'center',
              fontSize: 60,
              color: '#ffffff'
            }
          ]
        }
      ]
    };

    this.ventasService.generarVideo(videoJson).subscribe({
      next: (resp) => {
        // depende de la API: puede devolver URL o jobId
        this.videoUrl = resp.videoUrl;
        this.cargando = false;
      },
      error: (err) => {
        console.error(err);
        this.cargando = false;
      }
    });
  }


  generarVideo() {

    const jsonVideo = {
      resolution: "full-hd",
      scenes: [
        {
          elements: [
            {
              type: "text",
              text: "Hola desde Angular 🚀",
              style: {
                fontSize: 64,
                color: "#ffffff"
              }
            }
          ],
          background: {
            color: "#000000"
          },
          duration: 5
        }
      ]
    };

    this.ventasService.crearVideo(jsonVideo).subscribe(response => {
      this.movieId = response.id;
      console.log('Video en proceso:', response);
    });
  }

  consultarEstado() {
    if (!this.movieId) return;

    this.ventasService.obtenerEstado(this.movieId).subscribe(response => {
      if (response.status === 'done') {
        this.videoUrl = response.movie.url;
      }
    });
  }


}
