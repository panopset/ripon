# TODO

Combine Skipper and Stone

# Ripon
Open source drawing instrument for Android.

# Layout

## default screen layout

    MainActivity activity_main
     include stone_list (default)
      editbar
      include_stone_list_scroller
    EditStoneActivity edit_stone_activity
     EditStoneFragment stone_cold_editor
     
## >= w900dp

    MainActivity activity_main
     include stone_list (w900dp)
      editbar
      include_stone_list_scroller
      stone_detail_container
       EditStoneFragment stone_cold_editor
