package abhishekti7.unicorn.filepicker.filesystem

object PreferencesConstants {
    // START fragments
    const val FRAGMENT_THEME = "theme"
    const val FRAGMENT_COLORS = "colors"
    const val FRAGMENT_FOLDERS = "sidebar_folders"
    const val FRAGMENT_QUICKACCESSES = "sidebar_quickaccess"
    const val FRAGMENT_ADVANCED_SEARCH = "advancedsearch"
    const val FRAGMENT_ABOUT = "about"
    const val FRAGMENT_FEEDBACK = "feedback"

    // END fragments
    // START preferences.xml constants
    const val PREFERENCE_INTELLI_HIDE_TOOLBAR = "intelliHideToolbar"
    const val PREFERENCE_SHOW_FILE_SIZE = "showFileSize"
    const val PREFERENCE_SHOW_PERMISSIONS = "showPermissions"
    const val PREFERENCE_SHOW_DIVIDERS = "showDividers"
    const val PREFERENCE_SHOW_HEADERS = "showHeaders"
    const val PREFERENCE_SHOW_GOBACK_BUTTON = "goBack_checkbox"
    const val PREFERENCE_SHOW_SIDEBAR_FOLDERS = "sidebar_folders_enable"
    const val PREFERENCE_SHOW_SIDEBAR_QUICKACCESSES = "sidebar_quickaccess_enable"
    const val PREFERENCE_ENABLE_MARQUEE_FILENAME = "enableMarqueeFilename"
    const val PREFERENCE_ROOT_LEGACY_LISTING = "legacyListing"
    const val PREFERENCE_DRAG_AND_DROP_PREFERENCE = "dragAndDropPreference"
    const val PREFERENCE_DRAG_AND_DROP_REMEMBERED = "dragOperationRemembered"
    const val PREFERENCE_CLEAR_OPEN_FILE = "clear_open_file"
    const val PREFERENCE_BOOKMARKS_ADDED = "books_added"
    const val PREFERENCE_TEXTEDITOR_NEWSTACK = "texteditor_newstack"
    const val PREFERENCE_SHOW_HIDDENFILES = "showHidden"
    const val PREFERENCE_SHOW_LAST_MODIFIED = "showLastModified"
    const val PREFERENCE_USE_CIRCULAR_IMAGES = "circularimages"
    const val PREFERENCE_ROOTMODE = "rootmode"
    const val PREFERENCE_CHANGEPATHS = "typeablepaths"
    const val PREFERENCE_GRID_COLUMNS = "columns"
    const val PREFERENCE_SHOW_THUMB = "showThumbs"
    const val PREFERENCE_CRYPT_MASTER_PASSWORD = "crypt_password"
    const val PREFERENCE_CRYPT_FINGERPRINT = "crypt_fingerprint"
    const val PREFERENCE_CRYPT_WARNING_REMEMBER = "crypt_remember"
    const val ENCRYPT_PASSWORD_FINGERPRINT = "fingerprint"
    const val ENCRYPT_PASSWORD_MASTER = "master"
    const val PREFERENCE_CRYPT_MASTER_PASSWORD_DEFAULT = ""
    const val PREFERENCE_CRYPT_FINGERPRINT_DEFAULT = false
    const val PREFERENCE_CRYPT_WARNING_REMEMBER_DEFAULT = false
    const val PREFERENCE_ZIP_EXTRACT_PATH = "extractpath"

    // END preferences.xml constants
    // START color_prefs.xml constants
    const val PREFERENCE_SKIN = "skin"
    const val PREFERENCE_SKIN_TWO = "skin_two"
    const val PREFERENCE_ACCENT = "accent_skin"
    const val PREFERENCE_ICON_SKIN = "icon_skin"
    const val PREFERENCE_CURRENT_TAB = "current_tab"
    const val PREFERENCE_COLORIZE_ICONS = "coloriseIcons"
    const val PREFERENCE_COLORED_NAVIGATION = "colorednavigation"
    const val PREFERENCE_RANDOM_COLOR = "random_checkbox"

    // END color_prefs.xml constants
    // START folders_prefs.xml constants
    const val PREFERENCE_SHORTCUT = "add_shortcut"

    // END folders_prefs.xml constants
    // START random preferences
    const val PREFERENCE_DIRECTORY_SORT_MODE = "dirontop"
    const val PREFERENCE_DRAWER_HEADER_PATH = "drawer_header_path"
    const val PREFERENCE_URI = "URI"
    const val PREFERENCE_HIDEMODE = "hidemode"
    const val PREFERENCE_VIEW = "view"
    const val PREFERENCE_NEED_TO_SET_HOME = "needtosethome"

    /** The value is an int with values RANDOM_INDEX, CUSTOM_INDEX, NO_DATA or [0, ...]  */
    const val PREFERENCE_COLOR_CONFIG = "color config"

    // END random preferences
    // START sort preferences
    const val PREFERENCE_SORTBY_ONLY_THIS = "sortby_only_this"
    const val PREFERENCE_APPLIST_SORTBY = "AppsListFragment.sortBy"
    const val PREFERENCE_APPLIST_ISASCENDING = "AppsListFragment.isAscending"

    // END sort preferences
    // drag and drop preferences
    const val PREFERENCE_DRAG_DEFAULT = 0
    const val PREFERENCE_DRAG_TO_SELECT = 1
    const val PREFERENCE_DRAG_TO_MOVE_COPY = 2
    const val PREFERENCE_DRAG_REMEMBER_COPY = "copy"
    const val PREFERENCE_DRAG_REMEMBER_MOVE = "move"
}
