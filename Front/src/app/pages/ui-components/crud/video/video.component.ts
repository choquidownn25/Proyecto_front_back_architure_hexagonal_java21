import { CommonModule } from '@angular/common';
import {  Component, ElementRef, ViewChild, OnInit } from '@angular/core';
import { YouTubePlayerModule } from '@angular/youtube-player';


@Component({
  selector: 'app-video',
  standalone: true,
  imports: [ CommonModule, YouTubePlayerModule],
  templateUrl: './video.component.html',
  styleUrl: './video.component.scss'
})
export class VideoComponent implements  OnInit{

  @ViewChild('videoPlayer') videoPlayer!: ElementRef<HTMLVideoElement>;
// Extract the ID from a URL like: https://www.youtube.com
  videoId = 'zltMTNeOYPQ';


  playPauseVideo() {
    const video: HTMLVideoElement = this.videoPlayer.nativeElement;
    if (video.paused || video.ended) {
      video.play();
    } else {
      video.pause();
    }
  }

  toggleMute() {
    const video: HTMLVideoElement = this.videoPlayer.nativeElement;
    video.muted = !video.muted;
  }
   ngOnInit(): void {
    // Required to load the IFrame API script
    const tag = document.createElement('script');
    tag.src = "https://www.youtube.com";
    document.body.appendChild(tag);
  }
}


