import { Component } from '@angular/core';
import { AppService } from './core/app.service';
import { Score } from './model/score';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Welcome to 23andMe!';

  profileId = '';
  token = '';
  markerScores: Score[];
  error: string;

  constructor(private appService: AppService) {
  }

  getNeanderthalScore() {
    this.appService.getMarkerScores(this.profileId, this.token).subscribe(
      markerScores => {
        this.markerScores = markerScores;
        this.error = null;
      },
      error => {
        this.error = "Error! Problem getting Marker details";
        this.markerScores = null;
      },
      () => console.log('Completed getting marker scores!')
    );
  }
}
