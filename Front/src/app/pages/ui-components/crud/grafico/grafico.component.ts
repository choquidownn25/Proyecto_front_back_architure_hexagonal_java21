import { Component, OnInit, ViewChild } from '@angular/core';
import { MaterialModule } from 'src/app/material.module';
import { CoreService } from 'src/app/services/core.service';
import { Venta } from './venta';
import {
  ApexAxisChartSeries,
  ApexChart,
  ChartComponent,
  ApexDataLabels,
  ApexPlotOptions,
  ApexYAxis,
  ApexTitleSubtitle,
  ApexXAxis,
  ApexFill,
  NgApexchartsModule
} from "ng-apexcharts";
export type ChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  yaxis: ApexYAxis;
  stroke: any;
  theme: ApexTheme;
  tooltip: ApexTooltip;
  dataLabels: ApexDataLabels;
  legend: ApexLegend;
  colors: string[];
  markers: any;
  grid: ApexGrid;
  plotOptions: ApexPlotOptions;
  fill: ApexFill;
  labels: string[];
  title: ApexTitleSubtitle;
};

export type LineChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  stroke: ApexStroke;
  tooltip: ApexTooltip;
  dataLabels: ApexDataLabels;
  colors: string[];
  markers: ApexMarkers;
  grid: ApexGrid;
  legend: ApexLegend;
};

export type BarChartOptions = {
  series: ApexAxisChartSeries;
  chart: ApexChart;
  xaxis: ApexXAxis;
  dataLabels: ApexDataLabels;
  plotOptions: ApexPlotOptions;
  colors: string[];
};



@Component({
  selector: 'app-grafico',
  imports: [MaterialModule, NgApexchartsModule],
  templateUrl: './grafico.component.html',
  styleUrl: './grafico.component.scss'
})
export class GraficoComponent implements OnInit {

  //@ViewChild('chart') chart: ChartComponent = Object.create(null);
  @ViewChild("chart") chart: ChartComponent;
  @ViewChild('chart') chartComponent!: ChartComponent;

  public lineChartOptions: Partial<ChartOptions> | any;
  public chartOptions: Partial<ChartOptions>;

  constructor(private ventasService: CoreService) {
    // Line chart.
    this.lineChartOptions = {
      series: [
        {
          name: 'Site A',
          data: [5, 6, 3, 7, 9, 10, 14, 12, 11, 9, 8, 7, 10, 6, 12, 10, 8],
        },
        {
          name: 'Site B',
          data: [1, 2, 8, 3, 4, 5, 7, 6, 5, 6, 4, 3, 3, 12, 5, 6, 3],
        },
      ],
      chart: {
        height: 300,
        type: 'line',
        fontFamily: 'DM Sans,sans-serif',
        foreColor: '#a1aab2',
        toolbar: {
          show: false,
        },
      },
      dataLabels: {
        enabled: false,
      },
      markers: {
        size: 3,
        strokeColors: 'transparent',
      },
      stroke: {
        curve: 'straight',
        width: '2',
      },
      colors: ['#06d79c', '#398bf7'],
      legend: {
        show: false,
      },
      grid: {
        show: true,
        strokeDashArray: 0,
        borderColor: 'rgba(0,0,0,0.1)',
      },
      xaxis: {
        type: 'category',
        categories: [
          '0',
          '2',
          '4',
          '6',
          '8',
          '10',
          '12',
          '14',
          '16',
          '18',
          '20',
          '22',
          '24',
          '26',
          '28',
          '30',
          '32',
        ],
      },
      tooltip: {
        theme: 'dark',
      },
    };
  }
  series: ApexAxisChartSeries = [];

  charts: {
    type: 'bar',   // ✅ OBLIGATORIO
    height: 350
  };

  xaxis: ApexXAxis = {
    categories: []
  };

  public barChartOptions: BarChartOptions = {
    series: [], // ✅ ahora ES ApexAxisChartSeries
    chart: {
      type: 'bar' as const,
      height: 350
    },
    xaxis: {
      categories: []
    },
    dataLabels: {
      enabled: false
    },
    plotOptions: {
      bar: {
        columnWidth: '45%',
        borderRadius: 4
      }
    },
    colors: ['#398bf7']
  };




  ngOnInit(): void {
    this.cargarDatos();
this.chartOptions = {
      series: [
        {
          name: "Inflation",
          data: [2.3, 3.1, 4.0, 10.1, 4.0, 3.6, 3.2, 2.3, 1.4, 0.8, 0.5, 0.2]
        }
      ],
      chart: {
        height: 350,
        type: "bar"
      },
      plotOptions: {
        bar: {
          dataLabels: {
            position: "top" // top, center, bottom
          }
        }
      },
      dataLabels: {
        enabled: true,
        formatter: function(val) {
          return val + "%";
        },
        offsetY: -20,
        style: {
          fontSize: "12px",
          colors: ["#304758"]
        }
      },

      xaxis: {
        categories: [
          "Jan",
          "Feb",
          "Mar",
          "Apr",
          "May",
          "Jun",
          "Jul",
          "Aug",
          "Sep",
          "Oct",
          "Nov",
          "Dec"
        ],
        position: "top",
        labels: {
          offsetY: -18
        },
        axisBorder: {
          show: false
        },
        axisTicks: {
          show: false
        },
        crosshairs: {
          fill: {
            type: "gradient",
            gradient: {
              colorFrom: "#D8E3F0",
              colorTo: "#BED1E6",
              stops: [0, 100],
              opacityFrom: 0.4,
              opacityTo: 0.5
            }
          }
        },
        tooltip: {
          enabled: true,
          offsetY: -35
        }
      },
      fill: {
        type: "gradient",
        gradient: {
          shade: "light",
          type: "horizontal",
          shadeIntensity: 0.25,
          gradientToColors: undefined,
          inverseColors: true,
          opacityFrom: 1,
          opacityTo: 1,
          stops: [50, 0, 100, 100]
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
          formatter: function(val) {
            return val + "%";
          }
        }
      },
      title: {
        text: "Monthly Inflation in Argentina, 2002",
        //floating: 0,
        offsetY: 320,
        align: "center",
        style: {
          color: "#444"
        }
      }
    };
  }


  cargarDatos(): void {
    this.ventasService.obtenerVentas().subscribe((venta: Venta) => {

      const monedas = ['usd', 'cop', 'gbp'];

      this.series = [
        {
          name: 'EUR',
          data: monedas.map(m => venta.eur[m])
        }
      ];

      this.xaxis = {
        categories: monedas.map(m => m.toUpperCase())
      };


    });
  }

  cargarDatosBar(): void {

    this.ventasService.obtenerVentas().subscribe(venta => {
      const monedas = ['usd', 'cop', 'gbp'];


      //this.simpleBarOptions.xaxis.categories = monedas.map(m => m.toUpperCase());

      this.barChartOptions.series = [{
        name: 'EUR',
        data: monedas.map(m => venta.eur[m])
      }];
      this.barChartOptions.xaxis.categories = monedas.map(m => m.toUpperCase());
    });

  }

}
