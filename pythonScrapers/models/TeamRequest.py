from pydantic import BaseModel

class TeamRequest(BaseModel):
    playerIds: dict[str, str]
    statType: str = "standard"
