package com.example.geekhub_3rd_homework;

import java.util.List;

import android.database.SQLException;
import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedDelete;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

public class ArticleDAO extends BaseDaoImpl<Article, Integer> {

	public boolean isLiked(Article _article) throws java.sql.SQLException {
		String id = _article.getId();
		QueryBuilder<Article, Integer> queryBuilder = queryBuilder();
		queryBuilder.where().eq("id", id);
		PreparedQuery<Article> preparedQuery = queryBuilder.prepare();
		List<Article> ArticleList = query(preparedQuery);
		if (!(ArticleList == null) && (ArticleList.size() == 1)) {
			Article article = query(preparedQuery).get(0);
			if (article.getId().equals(id)) {
				return true;
			} else {
				return false;
			}
		} else {
			Log.d(LOG_TAG, "There is  " + ArticleList.size() + "elements of"
					+ id);
			Log.d(LOG_TAG,
					"lastIndexOf ArticleList is  "
							+ ArticleList.lastIndexOf(ArticleList));
			return false;
		}
	}

	public void deleteLike(String id) throws SQLException,
			java.sql.SQLException {
		DeleteBuilder<Article, Integer> deleteBuilder = deleteBuilder();
		deleteBuilder.where().eq("id", id);
		PreparedDelete<Article> query = deleteBuilder.prepare();
		Log.d(LOG_TAG, "trying to delete  " + id + deleteBuilder.toString());
		delete(query);

	}

	private static final String LOG_TAG = "ArticleDAO";

	protected ArticleDAO(ConnectionSource connectionSource,
			Class<Article> dataClass) throws SQLException,
			java.sql.SQLException {
		super(connectionSource, dataClass);
	}

	public List<Article> getAllArticle() throws SQLException,
			java.sql.SQLException {
		return this.queryForAll();
	}

	public boolean ifIsLikes() throws SQLException, java.sql.SQLException {
//		Log.d(LOG_TAG, "Try to execute " + " Likes");
//		List<Article> list = this.queryForAll();
//		Log.d(LOG_TAG, "there is " + lastIndexOf(list) + " Likes");
		if (this.queryForAll().isEmpty()) {
			Log.d(LOG_TAG, "there is  no Likes");
			return false;
		} else {
			Log.d(LOG_TAG, "there is some Likes");
			return true;
			
		}
	}

	private String lastIndexOf(List<Article> list) {
		// TODO Auto-generated method stub
		return null;
	}

}