import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Match } from './models/match';

@Injectable({
  providedIn: 'root'
})
export class MatchService {
  private apiUrl = 'http://localhost:8080/api/games/furia';

  constructor(private http: HttpClient) { }

  getFuriaMatches(): Observable<Match[]> {
    return this.http.get<Match[]>(this.apiUrl);
  }
}
