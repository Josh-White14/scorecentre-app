import ScraperFC as sfc

class FbScraper:
    def __init__(self):
        self.fbref = sfc.FBref()

    def scrape_match(self, url: str):
        return self.fbref.scrape_match(url)

    def scrape_stats(self, season: str, league: str, stat_type: str = "standard"):
        return self.fbref.scrape_stats(season, league, stat_type)

    def close(self):
        self.fbref.close()