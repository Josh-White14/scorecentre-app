from fastapi import FastAPI, HTTPException
from search.MatchPlayer import match_player
from models.TeamRequest import TeamRequest
import scrapers.fbRef as fb
import asyncio



app = FastAPI()
print("Creating FbScraper...")
fbScraper = fb.FbScraper()
print("FbScraper created!")

# TODO: HARDCODED FOR CURRENT STATE
LEAGUE = "England Premier League"
SEASON = "2025-2026"

@app.get("/ping")
async def ping():
    return {"pong": True}

@app.post("/team/players/{team}")
async def get_team_players(team: str, body: TeamRequest):
    try:
       print(f"Starting scrape for {team}...")
       _, _, player_df = await asyncio.to_thread(
        fbScraper.scrape_stats, SEASON, LEAGUE, body.statType
    )
        
    except Exception as e:
        print(f"Error: {e}")
        raise HTTPException(status_code=502, detail=f"FBref scrape failed: {str(e)}")

    team_df = player_df[player_df["team"].str.lower() == team.lower()]

    if team_df.empty:
        raise HTTPException(status_code=404, detail=f"No data found for team: {team}")

    matched, unmatched = [], []

    for _, row in team_df.iterrows():
        fbref_name = row["player"]
        result = match_player(fbref_name, body.playerIds)
        if result:
            player_id, db_name, score = result
            matched.append({
                "playerId": player_id,
                "fbrefName": fbref_name,
                "dbName": db_name,
                "matchScore": score,
                "stats": row.to_dict()
            })
        else:
            unmatched.append(fbref_name)

    return {"team": team, "season": SEASON, "statType": body.statType, "matched": matched, "unmatched": unmatched}

