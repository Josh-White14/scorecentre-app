from rapidfuzz import process, fuzz
import os

THRESHOLD = int(os.getenv("MATCH_THRESHOLD", 80))


def match_player(fbref_name: str, player_ids: dict):
    name_to_id = {v: k for k, v in player_ids.items()}
    result = process.extractOne(fbref_name, name_to_id.keys(), scorer=fuzz.token_sort_ratio)
    if result and result[1] >= THRESHOLD:
        return name_to_id[result[0]], result[0], result[1]
    return None

def match_player(fbref_name: str, player_ids: dict):
    name_to_id = {v: k for k, v in player_ids.items()}
    result = process.extractOne(fbref_name, name_to_id.keys(), scorer=fuzz.token_sort_ratio)
    if result and result[1] >= THRESHOLD:
        return name_to_id[result[0]], result[0], result[1]
    return None