import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { RouterLink } from '@angular/router';
import { Subscription } from 'rxjs';
import { MatchService } from '../match.service';
import { Match } from '../models/match';

@Component({
  selector: 'app-landing-page',
  standalone: true,
  imports: [CommonModule, HttpClientModule, RouterLink],
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.scss']
})
export class LandingPageComponent implements OnInit, OnDestroy {
  furiaMatches: Match[] = [];
  private matchesSubscription?: Subscription;

  initialDisplayCount: number = 6;
  showAllMatches: boolean = false;

  constructor(private matchService: MatchService) {}

  ngOnInit(): void {
    this.matchesSubscription = this.matchService.getFuriaMatches().subscribe(
      (data: Match[]) => {
        this.furiaMatches = data.map(match => ({
          ...match,
          result: match.result ? match.result.replace(/\s*\n\s*/g, ' - ').trim() : 'vs'
        }));
      },
      (error: any) => {
        console.error('Erro ao buscar partidas da FURIA:', error);
      },
      () => {
        console.log('Busca de partidas completa.');
      }
    );
  }

  ngOnDestroy(): void {
    this.matchesSubscription?.unsubscribe();
  }

  get matchesToDisplay(): Match[] {
    if (this.showAllMatches) {
      return this.furiaMatches;
    } else {
      return this.furiaMatches.slice(0, this.initialDisplayCount);
    }
  }

  toggleShowMatches(): void {
    this.showAllMatches = !this.showAllMatches;
  }

  get showMoreButtonVisible(): boolean {
    return this.furiaMatches.length > this.initialDisplayCount;
  }

  get isLoading(): boolean {
    return this.furiaMatches.length === 0 && this.matchesSubscription !== undefined && !this.matchesSubscription.closed;
  }

  get noMatchesFound(): boolean {
     return this.furiaMatches.length === 0 && this.matchesSubscription !== undefined && this.matchesSubscription.closed;
  }
}