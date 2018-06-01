import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs';
import { Score } from '../model/score';

@Injectable()
export class AppService {

  progress$;
  progressObserver;
  progress;

  constructor(private http: Http) {
    this.progress$ = Observable.create(observer => {
      this.progressObserver = observer;
    }).share();
  }

  getMarkerScores(profileId: string, token: string): Observable<Score[]> {
    return this.http.get('http://localhost:8080/marker-details?profileId='+profileId+'&token='+token)
      .map(this.extractData);
  }

  private extractData(res: Response) {
    if (res.status < 200 || res.status >= 300) {
      throw new Error('Bad response status: ' + res.status);
    }
    return res.json();
  }

}
