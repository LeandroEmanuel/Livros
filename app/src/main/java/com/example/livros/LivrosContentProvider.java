package com.example.livros;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LivrosContentProvider extends ContentProvider {
    private static final String AUTORIDADE = "com.example.livros";
    private static final String CATEGORIAS = "categorias";
    private static final String LIVROS = "livros";

    private static final  Uri ENDERECO_BASE = Uri.parse("content://" + AUTORIDADE);
    public static final  Uri ENDERECO_CATEGORAS = Uri.withAppendedPath(ENDERECO_BASE, CATEGORIAS);
    public static final  Uri ENDERECO_LIVROS = Uri.withAppendedPath(ENDERECO_BASE, LIVROS);

    private static final int URI_CATEGORIAS = 100;
    private static final int URI_ID_CATEGORIA = 101;
    private static final int URI_LIVROS = 200;
    private static final int URI_ID_LIVRO = 201;
    private static final String CURSOR_DIR = "vnd.android.cursor.dir/";
    private static final String CURSOR_ITEM = "vnd.android.cursor.item";
    private BdLivrosOpenHelper openHelper;
    
    
    private UriMatcher getUriMAcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTORIDADE, CATEGORIAS, URI_CATEGORIAS);
        uriMatcher.addURI(AUTORIDADE, CATEGORIAS + "/#", URI_ID_CATEGORIA);

        uriMatcher.addURI(AUTORIDADE, LIVROS, URI_LIVROS);
        uriMatcher.addURI(AUTORIDADE, LIVROS + "/#", URI_ID_LIVRO);

        return uriMatcher;
    }
    
    
    /**
     * Implement this to initialize your content provider on startup.
     * This method is called for all registered content providers on the
     * application main thread at application launch time.  It must not perform
     * lengthy operations, or application startup will be delayed.
     *
     * <p>You should defer nontrivial initialization (such as opening,
     * upgrading, and scanning databases) until the content provider is used
     * (via {@link #query}, {@link #insert}, etc).  Deferred initialization
     * keeps application startup fast, avoids unnecessary work if the provider
     * turns out not to be needed, and stops database errors (such as a full
     * disk) from halting application launch.
     *
     * <p>If you use SQLite, {@link SQLiteOpenHelper}
     * is a helpful utility class that makes it easy to manage databases,
     * and will automatically defer opening until first use.  If you do use
     * SQLiteOpenHelper, make sure to avoid calling
     * {@link SQLiteOpenHelper#getReadableDatabase} or
     * {@link SQLiteOpenHelper#getWritableDatabase}
     * from this method.  (Instead, override
     * {@link SQLiteOpenHelper#onOpen} to initialize the
     * database when it is first opened.)
     *
     * @return true if the provider was successfully loaded, false otherwise
     */
    @Override
    public boolean onCreate() {
        openHelper = new BdLivrosOpenHelper(getContext());
        
        return true;
    }

    /**
     * Implement this to handle query requests from clients.
     *
     * <p>Apps targeting {@link Build.VERSION_CODES#O} or higher should override
     * {@link #query(Uri, String[], Bundle, CancellationSignal)} and provide a stub
     * implementation of this method.
     *
     * <p>This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     * <p>
     * Example client call:<p>
     * <pre>// Request a specific record.
     * Cursor managedCursor = managedQuery(
     * ContentUris.withAppendedId(Contacts.People.CONTENT_URI, 2),
     * projection,    // Which columns to return.
     * null,          // WHERE clause.
     * null,          // WHERE clause value substitution
     * People.NAME + " ASC");   // Sort order.</pre>
     * Example implementation:<p>
     * <pre>// SQLiteQueryBuilder is a helper class that creates the
     * // proper SQL syntax for us.
     * SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
     *
     * // Set the table we're querying.
     * qBuilder.setTables(DATABASE_TABLE_NAME);
     *
     * // If the query ends in a specific record number, we're
     * // being asked for a specific record, so set the
     * // WHERE clause in our query.
     * if((URI_MATCHER.match(uri)) == SPECIFIC_MESSAGE){
     * qBuilder.appendWhere("_id=" + uri.getPathLeafId());
     * }
     *
     * // Make the query.
     * Cursor c = qBuilder.query(mDb,
     * projection,
     * selection,
     * selectionArgs,
     * groupBy,
     * having,
     * sortOrder);
     * c.setNotificationUri(getContext().getContentResolver(), uri);
     * return c;</pre>
     *
     * @param uri           The URI to query. This will be the full URI sent by the client;
     *                      if the client is requesting a specific record, the URI will end in a record number
     *                      that the implementation should parse and add to a WHERE or HAVING clause, specifying
     *                      that _id value.
     * @param projection    The list of columns to put into the cursor. If
     *                      {@code null} all columns are included.
     * @param selection     A selection criteria to apply when filtering rows.
     *                      If {@code null} then all rows are included.
     * @param selectionArgs You may include ?s in selection, which will be replaced by
     *                      the values from selectionArgs, in order that they appear in the selection.
     *                      The values will be bound as Strings.
     * @param sortOrder     How the rows in the cursor should be sorted.
     *                      If {@code null} then the provider is free to define the sort order.
     * @return a Cursor or {@code null}.
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase bd = openHelper.getReadableDatabase();

        //content://com.exemple/livros/3

        String id = uri.getLastPathSegment();// para apanhar o id do livro ou o id da categoria

        switch(getUriMAcher().match(uri)){
            case URI_CATEGORIAS:
                return new BdTableCategorias(bd).query(projection, selection, selectionArgs, null, null,sortOrder);

            case URI_ID_CATEGORIA:
                return new BdTableCategorias(bd).query(projection, BdTableCategorias._ID + "=?", new String[]{id}, null, null,sortOrder);

            case URI_LIVROS:
                return new BdTableLivros(bd).query(projection, selection, selectionArgs, null, null,sortOrder);

            case URI_ID_LIVRO:
                return new BdTableLivros(bd).query(projection, BdTableLivros._ID + "=?", new String[]{id}, null, null,sortOrder);

            default:
                throw new UnsupportedOperationException("Enderesso de query inválido (QUERY): " + uri.getPath());//erro para quando o enderesso não é valido
        }
    }

    /**
     * Implement this to handle requests for the MIME type of the data at the
     * given URI.  The returned MIME type should start with
     * <code>vnd.android.cursor.item</code> for a single record,
     * or <code>vnd.android.cursor.dir/</code> for multiple items.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     *
     * <p>Note that there are no permissions needed for an application to
     * access this information; if your content provider requires read and/or
     * write permissions, or is not exported, all applications can still call
     * this method regardless of their access permissions.  This allows them
     * to retrieve the MIME type for a URI when dispatching intents.
     *
     * @param uri the URI to query.
     * @return a MIME type string, or {@code null} if there is no type.
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int codigoUri = getUriMAcher().match(uri);

        switch(codigoUri){
            case URI_CATEGORIAS:
                return CURSOR_DIR + CATEGORIAS;
            case URI_ID_CATEGORIA:
                return CURSOR_ITEM + CATEGORIAS;
            case URI_LIVROS:
                return CURSOR_DIR + LIVROS;
            case URI_ID_LIVRO:
                return CURSOR_ITEM + LIVROS;
            default:// NO_MATCH
                return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase bd = openHelper.getWritableDatabase();

        long id;


        switch(getUriMAcher().match(uri)) {
            case URI_CATEGORIAS:
                id = new BdTableCategorias(bd).insert(values);
                break;

            case URI_LIVROS:
                id = new BdTableLivros(bd).insert(values);
                break;

            default:
                throw new UnsupportedOperationException("Enderesso de insert inválido (INSERT): " + uri.getPath());//erro para quando o enderesso não é valido
        }
        if (id == -1) {
            throw new SQLException("Não foi possivel inserir o registo" + uri.getPath());
        }
        //com.exemple.livros/livros/5

        return  Uri.withAppendedPath(uri, String.valueOf(id));
    }

    /**
     * Implement this to handle requests to delete one or more rows.
     * The implementation should apply the selection clause when performing
     * deletion, allowing the operation to affect multiple rows in a directory.
     * As a courtesy, call {@link ContentResolver#notifyChange(Uri, ContentObserver) notifyChange()}
     * after deleting.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     *
     * <p>The implementation is responsible for parsing out a row ID at the end
     * of the URI, if a specific row is being deleted. That is, the client would
     * pass in <code>content://contacts/people/22</code> and the implementation is
     * responsible for parsing the record number (22) when creating a SQL statement.
     *
     * @param uri           The full URI to query, including a row ID (if a specific record is requested).
     * @param selection     An optional restriction to apply to rows when deleting.
     * @param selectionArgs
     * @return The number of rows affected.
     * @throws SQLException
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase bd = openHelper.getWritableDatabase();

        String id = uri.getLastPathSegment();

        switch(getUriMAcher().match(uri)) {
            case URI_ID_CATEGORIA:
                return new BdTableCategorias(bd).delete(BdTableCategorias._ID + "=?", new String[]{id});

            case URI_ID_LIVRO:
                return new BdTableCategorias(bd).delete(BdTableCategorias._ID + "=?", new String[]{id});

            default:
                throw new UnsupportedOperationException("Enderesso de delete inválido (DELETE): " + uri.getPath());//erro para quando o enderesso não é valido
        }

    }

    /**
     * Implement this to handle requests to update one or more rows.
     * The implementation should update all rows matching the selection
     * to set the columns according to the provided values map.
     * As a courtesy, call {@link ContentResolver#notifyChange(Uri, ContentObserver) notifyChange()}
     * after updating.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     *
     * @param uri           The URI to query. This can potentially have a record ID if this
     *                      is an update request for a specific record.
     * @param values        A set of column_name/value pairs to update in the database.
     *                      This must not be {@code null}.
     * @param selection     An optional filter to match rows to update.
     * @param selectionArgs
     * @return the number of rows affected.
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase bd = openHelper.getWritableDatabase();

        String id = uri.getLastPathSegment();

        switch(getUriMAcher().match(uri)) {
            case URI_ID_CATEGORIA:
                return new BdTableCategorias(bd).update(values,BdTableCategorias._ID + "=?", new String[]{id});

            case URI_ID_LIVRO:
                return new BdTableCategorias(bd).update(values,BdTableCategorias._ID + "=?", new String[]{id});

            default:
                throw new UnsupportedOperationException("Enderesso de update invalido inválidO (UPDATE): " + uri.getPath());//erro para quando o enderesso não é valido
        }
    }
}
