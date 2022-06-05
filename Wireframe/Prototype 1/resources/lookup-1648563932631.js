(function(window, undefined) {
  var dictionary = {
    "e6bf8795-6396-453a-8500-0c2e1e2914d5": "Balance Adm",
    "dcf1c213-6375-4540-bf96-4b4df7a9a6e4": "Signin Ad",
    "90e63853-e7da-4060-af79-ef8a8a271435": "Menu",
    "5e06768f-394f-4134-a45c-7d05730a5cd6": "Sign out",
    "0750c4b3-e82a-4624-9e59-7debd914f1e3": "Reservation Ad",
    "889ef8be-a8f3-4f62-99bc-193c49065868": "SF admin",
    "0d6a84cd-988c-46a5-b03d-4c3973f9b254": "Login Adm",
    "e58d4537-b5fe-4c9a-9c5d-f018888c324b": "Check reservations",
    "98394a14-1943-4f71-8345-2dae0e7caf3e": "Admin options",
    "d7f5583b-6815-418c-97d2-9a283d9aa5c6": "Reservation ok",
    "a981a9af-dd70-4866-917e-aed0436fb78a": "Signin Succeful Ad",
    "9cbc659e-99dd-4708-b1cf-38e62eaad878": "SF list",
    "9e496527-e9af-4194-8a82-167b8dff0322": "Update SF info",
    "6bb13e4c-944c-411e-8b47-a7f04367c62e": "Reservation confirmation",
    "a62ba331-3996-47e4-a952-29fab5f54e3f": "PW update",
    "0ce7a5c8-67e8-40e3-8860-98d2b03d2114": "Reservation info",
    "bb95cbf6-bfb1-446a-9e18-86aa4fc910a6": "Fields list Adm",
    "3fa691a9-c9b9-4635-8cfc-46dcc9a3d6b9": "Cancel reservation",
    "99a3680e-c66a-4629-b0e7-6ea9b60e4cd2": "Field filters",
    "32ad017c-abac-4de5-8f88-84741a244f9b": "SF info",
    "d12245cc-1680-458d-89dd-4f0d7fb22724": "Screen 1",
    "f39803f7-df02-4169-93eb-7547fb8c961a": "Template 1",
    "bb8abf58-f55e-472d-af05-a7d1bb0cc014": "default"
  };

  var uriRE = /^(\/#)?(screens|templates|masters|scenarios)\/(.*)(\.html)?/;
  window.lookUpURL = function(fragment) {
    var matches = uriRE.exec(fragment || "") || [],
        folder = matches[2] || "",
        canvas = matches[3] || "",
        name, url;
    if(dictionary.hasOwnProperty(canvas)) { /* search by name */
      url = folder + "/" + canvas;
    }
    return url;
  };

  window.lookUpName = function(fragment) {
    var matches = uriRE.exec(fragment || "") || [],
        folder = matches[2] || "",
        canvas = matches[3] || "",
        name, canvasName;
    if(dictionary.hasOwnProperty(canvas)) { /* search by name */
      canvasName = dictionary[canvas];
    }
    return canvasName;
  };
})(window);