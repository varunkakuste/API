import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Config } from '../shared/config';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/toPromise';
import './rxjs-operators';

export function ConfigLoader(configService: ConfigService) {
  return () => configService.initConfig();
}

@Injectable()
export class ConfigService {
  public config: Config;

  constructor(private http: Http) { }

  public initConfig(): Promise<Config> {
    let promise = this.http.get('assets/config.json')
      .map(res => res.json())
      .toPromise();
    promise.then(config => this.config = config);
    return promise;
  }

  public getApiServerUrl(): string {
    return this.config.apiServerUrl;
  }
}
